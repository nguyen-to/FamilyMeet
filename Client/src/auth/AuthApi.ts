import type {
  AuthFromResponse,
  AuthRegisterRequest,
  ResetPasswordForm,
} from "../formstyle/AuthForm";
import type { DataResponseSuccess } from "../utils/DataResponseSuccess";
import Http from "./Https";

export const AuthApi = {
  LoginApi: (body: { email: string; password: string; deviceId: string }) =>
    Http.post<DataResponseSuccess<AuthFromResponse>>("auth/login", body),
  LoginFirebaseApi: (body: {
    email: string;
    password: string;
    deviceId: string;
  }) => Http.post<DataResponseSuccess<AuthFromResponse>>("auth/firebase", body),
  RegisterApi: (body: AuthRegisterRequest) =>
    Http.post<DataResponseSuccess<string>>("auth/register", body),
  RefreshTokenApi: (params: { deviceId: string }) =>
    Http.post<DataResponseSuccess<AuthFromResponse>>(
      "auth/refresh-token",
      params
    ),
    ChangePasswordApi: (body: ResetPasswordForm) => Http.post<DataResponseSuccess<string>>("auth/changePassword", body),
};
