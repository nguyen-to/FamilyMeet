import { useState } from "react";
import {
  Users,
  Eye,
  EyeOff,
  Mail,
  Lock,
  ArrowLeft,
  User,
  Phone,
  Check,
} from "lucide-react";
import { Link, useNavigate } from "@tanstack/react-router";
import BackgrondDecoration from "../../component/backgrounddecoration";
import InputType from "../../component/inputype/InputType";
import SocialAuth from "../../component/socialauth/SocialAuth";
import { AuthApi } from "../../auth/AuthApi";
import type { AuthRegisterRequest } from "../../formstyle/AuthForm";
import { toast } from "react-toastify";
type LoginForm = {
  email: string;
  password: string;
  deviceId: string;
};
export default function FamilyMeetRegister() {
  const [showPassword, setShowPassword] = useState(false);
  const [showConfirmPassword, setShowConfirmPassword] = useState(false);
  const Navigate = useNavigate();
  const [formData, setFormData] = useState({
    fullName: "",
    email: "",
    phone: "",
    password: "",
    confirmPassword: "",
  });

  const handleInputChange = (field: string, value: string) => {
    setFormData((prev) => ({
      ...prev,
      [field]: value,
    }));
  };

  const handleSubmit = (event: React.FormEvent) => {
    event.preventDefault();
    const data: AuthRegisterRequest = {
      fullName: formData.fullName,
      email: formData.email,
      phone: formData.phone,
      password: formData.password,
    };
    AuthApi.RegisterApi(data)
      .then((response) => {
        if (response.data.message === "Register Successful") {
          Navigate({
            to: "/auth/login",
          });
        } else {
          toast.error("Email đã được sử dụng.");
        }
      })
      .catch(() => {
        toast.error("Email đã được sử dụng, vui lòng thử lại.");
      });
  };
  const HandleSuccess = (data: { email: string; tokenFirebase: string }) => {
    const loginData: LoginForm = {
      email: data.email,
      password: data.tokenFirebase,
      deviceId: Math.floor(100000 + Math.random() * 900000).toString(),
    };
    AuthApi.LoginFirebaseApi(loginData)
      .then(() => {
        Navigate({
          to: "/",
        });
      })
      .catch(() => {
        toast.error("Đăng nhập thất bại, vui lòng thử lại sau.");
      });
  };
  const HandleError = (error: string) => {
    console.error("Social login error:", error);
  };
  const isPasswordMatch =
    formData.password &&
    formData.confirmPassword &&
    formData.password === formData.confirmPassword;
  const isFormValid =
    formData.fullName &&
    formData.email &&
    formData.phone &&
    formData.password &&
    formData.confirmPassword &&
    isPasswordMatch;

  return (
    <div className="min-h-screen bg-gradient-to-br from-blue-600 via-purple-600 to-indigo-800 flex items-center justify-center p-4">
      <BackgrondDecoration />
      <div className="relative w-full max-w-md">
        <button className="absolute -top-16 left-0 flex items-center space-x-2 text-white/80 hover:text-white transition-colors">
          <ArrowLeft className="h-5 w-5" />
          <span>Về Trang Đăng Nhập</span>
        </button>

        {/* Register Card */}
        <div className="bg-white/10 backdrop-blur-lg rounded-3xl p-8 shadow-2xl border border-white/20">
          <div className="text-center mb-8">
            <div className="flex justify-center mb-4">
              <div className="bg-gradient-to-r from-green-400 to-blue-500 p-3 rounded-2xl">
                <Users className="h-10 w-10 text-white" />
              </div>
            </div>
            <h1 className="text-3xl font-bold text-white mb-2">
              Tạo Tài Khoản
            </h1>
            <p className="text-white/70">
              Tham gia cộng đồng FamilyMeet ngay hôm nay
            </p>
          </div>

          {/* Register Form */}
          <form onSubmit={handleSubmit} className="space-y-5">
            {/* Full Name Field */}
            <InputType
              type="text"
              value={formData.fullName}
              onChange={(value) => handleInputChange("fullName", value)}
              label="Họ và tên"
              icon={User}
              placeholder="Nhập họ và tên của bạn"
              required
            />

            {/* Email Field */}
            <InputType
              type="email"
              value={formData.email}
              onChange={(value) => handleInputChange("email", value)}
              label="Email"
              icon={Mail}
              placeholder="Nhập email của bạn"
              required
            />

            {/* Phone Field */}
            <InputType
              type="tel"
              value={formData.phone}
              onChange={(value) => handleInputChange("phone", value)}
              label="Số điện thoại"
              icon={Phone}
              placeholder="Nhập số điện thoại"
              required
            />

            {/* Password Field */}
            <div className="space-y-2">
              <InputType
                type={showPassword ? "text" : "password"}
                value={formData.password}
                onChange={(value) => handleInputChange("password", value)}
                label="Mật khẩu"
                icon={Lock}
                placeholder="Tạo mật khẩu mạnh"
                required
                rightIcon={
                  <button
                    type="button"
                    onClick={() => setShowPassword(!showPassword)}
                  >
                    {showPassword ? (
                      <EyeOff className="h-5 w-5 text-white/50 hover:text-white/70 transition-colors" />
                    ) : (
                      <Eye className="h-5 w-5 text-white/50 hover:text-white/70 transition-colors" />
                    )}
                  </button>
                }
              />
              {/* Password Strength Indicator */}
              {formData.password && (
                <div className="mt-2">
                  <div className="flex space-x-1">
                    <div
                      className={`h-1 w-1/4 rounded ${formData.password.length >= 6 ? "bg-green-400" : "bg-white/20"}`}
                    ></div>
                    <div
                      className={`h-1 w-1/4 rounded ${formData.password.length >= 8 ? "bg-green-400" : "bg-white/20"}`}
                    ></div>
                    <div
                      className={`h-1 w-1/4 rounded ${/[A-Z]/.test(formData.password) ? "bg-green-400" : "bg-white/20"}`}
                    ></div>
                    <div
                      className={`h-1 w-1/4 rounded ${/[0-9]/.test(formData.password) ? "bg-green-400" : "bg-white/20"}`}
                    ></div>
                  </div>
                  <p className="text-xs text-white/60 mt-1">
                    Ít nhất 8 ký tự, bao gồm chữ hoa và số
                  </p>
                </div>
              )}
            </div>

            {/* Confirm Password Field */}
            <InputType
              type={showConfirmPassword ? "text" : "password"}
              value={formData.confirmPassword}
              onChange={(value) => handleInputChange("confirmPassword", value)}
              label="Xác nhận mật khẩu"
              icon={Lock}
              placeholder="Nhập lại mật khẩu"
              required
              error={
                formData.confirmPassword && !isPasswordMatch
                  ? "Mật khẩu không khớp"
                  : ""
              }
              className={
                formData.confirmPassword && isPasswordMatch
                  ? "border-green-400"
                  : ""
              }
              rightIcon={
                <>
                  {formData.confirmPassword && (
                    <div className="absolute inset-y-0 right-10 flex items-center">
                      {isPasswordMatch ? (
                        <Check className="h-4 w-4 text-green-400" />
                      ) : (
                        <div className="h-4 w-4 rounded-full border-2 border-red-400"></div>
                      )}
                    </div>
                  )}
                  <button
                    type="button"
                    onClick={() => setShowConfirmPassword(!showConfirmPassword)}
                  >
                    {showConfirmPassword ? (
                      <EyeOff className="h-5 w-5 text-white/50 hover:text-white/70 transition-colors" />
                    ) : (
                      <Eye className="h-5 w-5 text-white/50 hover:text-white/70 transition-colors" />
                    )}
                  </button>
                </>
              }
            />

            {/* Register Button */}
            <button
              type="submit"
              disabled={!isFormValid}
              className={`w-full py-3 rounded-xl font-semibold transition-all shadow-lg ${
                isFormValid
                  ? "bg-gradient-to-r from-green-400 to-blue-500 text-white hover:from-green-500 hover:to-blue-600 transform hover:scale-105"
                  : "bg-white/10 text-white/50 cursor-not-allowed"
              }`}
            >
              Tạo Tài Khoản
            </button>

            <SocialAuth
              buttonText={{ google: "Google", facebook: "FaceBook" }}
              text="hoặc đăng ký với"
              onSuccess={HandleSuccess}
              onError={HandleError}
            />

            {/* Login Link */}
            <div className="text-center">
              <p className="text-white/70">
                Đã có tài khoản?{" "}
                <Link
                  to="/auth/login"
                  className="text-green-300 hover:text-green-200 font-semibold transition-colors"
                >
                  Đăng nhập ngay
                </Link>
              </p>
            </div>
          </form>
        </div>
      </div>
    </div>
  );
}
