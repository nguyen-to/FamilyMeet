import { signInWithPopup } from "firebase/auth";
import { auth, facebookProvider, googleProvider } from "../config/Firebase";

interface AuthResponse {
  success: boolean;
  data?: {
    email: string;
    tokenFirebase: string;
  };
  error?: string;
}

// Đăng nhập bằng Google và lấy token
export const signInWithGoogle = async (): Promise<AuthResponse> => {
  try {
    const result = await signInWithPopup(auth, googleProvider);
    const user = result.user;

    const tokenFirebase = await user.getIdToken();

    console.log("Đăng nhập Google thành công:", {
      email: user.email,
      tokenFirebase: tokenFirebase,
    });

    return {
      success: true,
      data: {
        email: user.email || "",
        tokenFirebase: tokenFirebase,
      },
    };
  } catch (error) {
    console.error("Lỗi đăng nhập Google:", error);
    return {
      success: false,
      error: "Đăng nhập Facebook thất bại",
    };
  }
};

// Đăng nhập bằng Facebook và lấy token
export const signInWithFacebook = async (): Promise<AuthResponse> => {
  try {
    const result = await signInWithPopup(auth, facebookProvider);
    const user = result.user;

    const tokenFirebase = await user.getIdToken();

    console.log("Đăng nhập Facebook thành công:", {
      email: user.email,
      tokenFirebase: tokenFirebase,
    });

    return {
      success: true,
      data: {
        email: user.email || "",
        tokenFirebase: tokenFirebase,
      },
    };
  } catch (error) {
    console.error("Lỗi đăng nhập Facebook:", error);
    return {
      success: false,
      error: "Đăng nhập Facebook thất bại",
    };
  }
};
