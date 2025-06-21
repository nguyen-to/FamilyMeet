import axios, {
  AxiosError,
  type AxiosInstance,
  type AxiosResponse,
} from "axios";
import {
  ClearAll,
  GetAccessToken,
  GetDeviceId,
  SaveAccessToken,
  SaveDeviceId,
  SaveProfileUser,
} from "./LocalStore";
import type { AuthFromResponse } from "../formstyle/AuthForm";

interface RefreshTokenResponse {
  data: {
    accessToken: string;
    refreshToken?: string;
  };
}

class http {
  instance: AxiosInstance;
  accessToken: string;
  deviceId: string;
  private isRefreshing: boolean = false;
  private failedQueue: Array<{
    // eslint-disable-next-line @typescript-eslint/no-explicit-any
    resolve: (value?: any) => void;
    // eslint-disable-next-line @typescript-eslint/no-explicit-any
    reject: (error?: any) => void;
  }> = [];

  constructor() {
    this.instance = axios.create({
      baseURL: "http://localhost:8000/api/",
      timeout: 10000,
      headers: {
        "Content-Type": "application/json",
        Accept: "application/json",
      },
      withCredentials: true,
    });

    this.accessToken = GetAccessToken() as string;
    this.deviceId = GetDeviceId() as string;

    this.setupInterceptors();
  }

  private setupInterceptors() {
    this.instance.interceptors.request.use(
      (config) => {
        if (this.accessToken && config.headers) {
          config.headers.Authorization = `Bearer ${this.accessToken}`;
        }
        return config;
      },
      (error) => {
        return Promise.reject(error);
      }
    );

    this.instance.interceptors.response.use(
      (response) => {
        this.handleSuccessResponse(response);
        return response;
      },
      async (error: AxiosError) => {
        // eslint-disable-next-line @typescript-eslint/no-explicit-any
        const originalRequest = error.config as any;

        // Xử lý lỗi 401 (Unauthorized) - token hết hạn
        if (error.response?.status === 401 && !originalRequest._retry) {
          if (this.isRefreshing) {
            // Nếu đang trong quá trình refresh, thêm request vào queue
            return new Promise((resolve, reject) => {
              this.failedQueue.push({ resolve, reject });
            })
              .then(() => {
                originalRequest.headers.Authorization = `Bearer ${this.accessToken}`;
                return this.instance(originalRequest);
              })
              .catch((err) => {
                return Promise.reject(err);
              });
          }

          originalRequest._retry = true;
          this.isRefreshing = true;

          try {
            const newAccessToken = await this.refreshAccessToken();

            if (newAccessToken) {
              // Cập nhật token mới
              this.accessToken = newAccessToken;
              originalRequest.headers.Authorization = `Bearer ${newAccessToken}`;

              // Xử lý tất cả requests đang chờ trong queue
              this.processQueue(null);

              // Thực hiện lại request ban đầu với token mới
              return this.instance(originalRequest);
            } else {
              throw new Error("Unable to refresh token");
            }
          } catch (refreshError) {
            // Refresh token cũng hết hạn hoặc có lỗi
            this.processQueue(refreshError);
            this.handleTokenExpired();
            return Promise.reject(refreshError);
          } finally {
            this.isRefreshing = false;
          }
        }

        console.log("API Error:", error);
        return Promise.reject(error);
      }
    );
  }

  private async refreshAccessToken(): Promise<string | null> {
    try {
      console.log("Attempting to refresh access token...");

      if (!this.deviceId) {
        throw new Error("Device ID not found");
      }

      // Tạo một axios instance riêng cho refresh token
      const refreshInstance = axios.create({
        baseURL: "http://localhost:8000/api/",
        timeout: 10000,
        headers: {
          "Content-Type": "application/json",
          Accept: "application/json",
        },
        withCredentials: true,
      });

      const response = await refreshInstance.post<RefreshTokenResponse>(
        "auth/refresh-token",
        { deviceId: this.deviceId }
      );

      if (response.status === 200 && response.data?.data?.accessToken) {
        const { accessToken } = response.data.data;

        SaveAccessToken(accessToken);

        console.log("Access token refreshed successfully");
        return accessToken;
      } else {
        throw new Error("Invalid refresh token response");
      }
    } catch (error) {
      console.error("Failed to refresh access token:", error);

      // Kiểm tra nếu refresh token cũng hết hạn
      if (axios.isAxiosError(error)) {
        const status = error.response?.status;
        if (status === 401 || status === 403) {
          console.log("Refresh token has expired or is invalid");
          throw new Error("Refresh token expired");
        }

        // Kiểm tra các lỗi khác có thể xảy ra
        if (status === 400) {
          console.log("Bad request - possibly invalid deviceId");
          throw new Error("Invalid refresh request");
        }
      }

      throw error;
    }
  }

  // eslint-disable-next-line @typescript-eslint/no-explicit-any
  private processQueue(error: any) {
    this.failedQueue.forEach(({ resolve, reject }) => {
      if (error) {
        reject(error);
      } else {
        resolve();
      }
    });

    this.failedQueue = [];
  }

  private handleTokenExpired() {
    console.log(
      "Both access token and refresh token have expired. Logging out..."
    );

    this.accessToken = "";
    this.deviceId = "";
    ClearAll();

    this.performLogout();

    window.dispatchEvent(new CustomEvent("auth:expired"));
  }

  private async performLogout() {
    try {
      // Gọi API logout để server clear refreshToken cookie
      await axios.post(
        "http://localhost:8000/api/auth/logout",
        {},
        {
          withCredentials: true,
          headers: {
            "Content-Type": "application/json",
            Accept: "application/json",
          },
        }
      );
      console.log("Logout successful - cookies cleared");
    } catch (error) {
      console.error("Logout request failed:", error);
    }
  }

  private handleSuccessResponse(response: AxiosResponse) {
    console.log("API Response:", response);
    const { url } = response.config;

    // Xử lý response từ login
    if (url === "auth/login" && response.status === 200) {
      const data = response.data.data as AuthFromResponse;
      this.saveAuthData(data);
    }

    // Xử lý response từ Firebase auth
    if (url === "auth/firebase" && response.status === 200) {
      const data = response.data.data as AuthFromResponse;
      console.log("Data from Firebase:", data);
      this.saveAuthData(data);
    }

    // Xử lý logout
    if (url === "auth/logout") {
      this.clearAuthData();
    }

    // Xử lý refresh token response
    if (url === "auth/refresh-token" && response.status === 200) {
      const data = response.data.data;
      if (data?.accessToken) {
        SaveAccessToken(data.accessToken);
        this.accessToken = data.accessToken;
        console.log("Token refreshed and saved");
      }
    }
  }

  private saveAuthData(data: AuthFromResponse) {
    SaveProfileUser({
      fullName: data.fullName,
      email: data.email,
      picture: data.picture,
    });
    SaveDeviceId(data.deviceId);
    SaveAccessToken(data.accessToken);

    this.accessToken = data.accessToken;
    this.deviceId = data.deviceId;
  }

  private clearAuthData() {
    this.accessToken = "";
    this.deviceId = "";
    ClearAll();
  }

  // Method để manually refresh token nếu cần
  public async manualRefreshToken(): Promise<boolean> {
    try {
      const newToken = await this.refreshAccessToken();
      return !!newToken;
    } catch (error) {
      console.error("Manual token refresh failed:", error);
      return false;
    }
  }

  public isTokenValid(): boolean {
    return !!this.accessToken && !!this.deviceId;
  }

  public async logout(): Promise<void> {
    try {
      await this.instance.post("auth/logout");
      this.clearAuthData();
    } catch (error) {
      console.error("Logout failed:", error);
      this.clearAuthData();
    }
  }
}

const Http = new http().instance;
export default Http;
