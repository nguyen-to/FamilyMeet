import { useState } from "react";
import {
  Users,
  Eye,
  EyeOff,
  Lock,
  ArrowLeft,
  Check,
  AlertCircle,
  Mail,
} from "lucide-react";
import { Link, useNavigate } from "@tanstack/react-router";
import BackgrondDecoration from "../../component/backgrounddecoration";
import InputType from "../../component/inputype/InputType";
import { toast } from "react-toastify";
import { AuthApi } from "../../auth/AuthApi";
import type { ResetPasswordForm } from "../../formstyle/AuthForm";

export default function ChangePassword() {
  const [showNewPassword, setShowNewPassword] = useState(false);
  const [showConfirmPassword, setShowConfirmPassword] = useState(false);
  const navigate = useNavigate();

  const [formData, setFormData] = useState({
    email: "",
    newpassword: "",
    confirmPassword: "",
  });

  const isPasswordStrong = (): boolean => {
    return (
      formData.newpassword.length >= 8 &&
      /[A-Z]/.test(formData.newpassword) &&
      /[0-9]/.test(formData.newpassword)
    );
  };
  const isPasswordMatch =
    formData.newpassword &&
    formData.confirmPassword &&
    formData.newpassword === formData.confirmPassword;

  const isFormValid =
    formData.email &&
    formData.newpassword &&
    formData.confirmPassword &&
    isPasswordMatch &&
    isPasswordStrong();
  const handleInputChange = (field: keyof typeof formData, value: string) => {
    setFormData((prev) => ({
      ...prev,
      [field]: value,
    }));
  };

  // Handle form submission
  const handleSubmit = async (event: React.FormEvent) => {
    event.preventDefault();

    const data: ResetPasswordForm = {
      email: formData.email,
      newPassword: formData.newpassword,
    };
    AuthApi.ChangePasswordApi(data)
      .then((response) => {
        if (response.data.message === "Change Password Successful") {
          toast.success("Đặt lại mật khẩu thành công.");
          navigate({
            to: "/auth/login",
            replace: true,
          });
        } else {
          toast.error("Email không đúng, vui lòng thử lại.");
        }
      })
      .catch(() => {
        toast.error("Đặt lại mật khẩu thất bại, vui lòng thử lại sau.");
       
      });
  };

  return (
    <div className="min-h-screen bg-gradient-to-br from-blue-600 via-purple-600 to-indigo-800 flex items-center justify-center p-4">
      <BackgrondDecoration />

      <div className="relative w-full max-w-md">
        {/* Back Button */}
        <button className="absolute -top-16 left-0 flex items-center space-x-2 text-white/80 hover:text-white transition-colors">
          <ArrowLeft className="h-5 w-5" />
          <Link to="/auth/login">Quay lại đăng nhập</Link>
        </button>

        {/* Reset Password Card */}
        <form
          onSubmit={handleSubmit}
          className="bg-white/10 backdrop-blur-lg rounded-3xl p-8 shadow-2xl border border-white/20"
        >
          {/* Header */}
          <div className="text-center mb-8">
            <div className="flex justify-center mb-4">
              <div className="bg-gradient-to-r from-yellow-400 to-orange-500 p-3 rounded-2xl">
                <Users className="h-10 w-10 text-white" />
              </div>
            </div>
            <h1 className="text-3xl font-bold text-white mb-2">
              Đặt Lại Mật Khẩu
            </h1>
            <p className="text-white/70">
              Tạo mật khẩu mới cho tài khoản FamilyMeet
            </p>
          </div>

          <div className="space-y-6">
            {/* Email Field */}
            <div className="space-y-2">
              <label className="text-white/90 text-sm font-medium">Email</label>
              <div className="relative">
                <div className="absolute inset-y-0 left-0 pl-3 flex items-center pointer-events-none">
                  <Mail className="h-5 w-5 text-white/50" />
                </div>
                <InputType
                  type="email"
                  value={formData.email}
                  onChange={(e) => handleInputChange("email", e)}
                  className={`w-full pl-10 pr-4 py-3 bg-white/10 border rounded-xl text-white placeholder-white/50 focus:outline-none focus:ring-2 focus:ring-yellow-400 focus:border-transparent transition-all `}
                  placeholder="Nhập email của bạn"
                  required
                />
              </div>
            </div>

            {/* New Password Field */}
            <div className="space-y-2">
              <label className="text-white/90 text-sm font-medium">
                Mật khẩu mới
              </label>
              <div className="relative">
                <div className="absolute inset-y-0 left-0 pl-3 flex items-center pointer-events-none">
                  <Lock className="h-5 w-5 text-white/50" />
                </div>
                <InputType
                  type={showNewPassword ? "text" : "password"}
                  value={formData.newpassword}
                  onChange={(e) => handleInputChange("newpassword", e)}
                  className="w-full pl-10 pr-12 py-3 bg-white/10 border border-white/20 rounded-xl text-white placeholder-white/50 focus:outline-none focus:ring-2 focus:ring-yellow-400 focus:border-transparent transition-all"
                  placeholder="Nhập mật khẩu mới"
                  required
                />
                <button
                  type="button"
                  onClick={() => setShowNewPassword(!showNewPassword)}
                  className="absolute inset-y-0 right-0 pr-3 flex items-center"
                >
                  {showNewPassword ? (
                    <EyeOff className="h-5 w-5 text-white/50 hover:text-white/70 transition-colors" />
                  ) : (
                    <Eye className="h-5 w-5 text-white/50 hover:text-white/70 transition-colors" />
                  )}
                </button>
              </div>

              {/* Password Strength Indicator */}
              {formData.newpassword && (
                <div className="mt-2">
                  <div className="flex space-x-1">
                    <div
                      className={`h-1 w-1/4 rounded ${formData.newpassword.length >= 6 ? "bg-green-400" : "bg-white/20"}`}
                    ></div>
                    <div
                      className={`h-1 w-1/4 rounded ${formData.newpassword.length >= 8 ? "bg-green-400" : "bg-white/20"}`}
                    ></div>
                    <div
                      className={`h-1 w-1/4 rounded ${/[A-Z]/.test(formData.newpassword) ? "bg-green-400" : "bg-white/20"}`}
                    ></div>
                    <div
                      className={`h-1 w-1/4 rounded ${/[0-9]/.test(formData.newpassword) ? "bg-green-400" : "bg-white/20"}`}
                    ></div>
                  </div>
                  <p className="text-xs text-white/60 mt-1">
                    Ít nhất 8 ký tự, bao gồm chữ hoa và số
                  </p>
                </div>
              )}
            </div>

            {/* Confirm Password Field */}
            <div className="space-y-2">
              <label className="text-white/90 text-sm font-medium">
                Xác nhận mật khẩu
              </label>
              <div className="relative">
                <div className="absolute inset-y-0 left-0 pl-3 flex items-center pointer-events-none">
                  <Lock className="h-5 w-5 text-white/50" />
                </div>
                <InputType
                  type={showConfirmPassword ? "text" : "password"}
                  value={formData.confirmPassword}
                  onChange={(e) => handleInputChange("confirmPassword", e)}
                  className={`w-full pl-10 pr-12 py-3 bg-white/10 border rounded-xl text-white placeholder-white/50 focus:outline-none focus:ring-2 focus:ring-yellow-400 focus:border-transparent transition-all ${
                    formData.confirmPassword && !isPasswordMatch
                      ? "border-red-400 focus:ring-red-400"
                      : formData.confirmPassword && isPasswordMatch
                        ? "border-green-400 focus:ring-green-400"
                        : "border-white/20"
                  }`}
                  placeholder="Nhập lại mật khẩu mới"
                  required
                />
                <button
                  type="button"
                  onClick={() => setShowConfirmPassword(!showConfirmPassword)}
                  className="absolute inset-y-0 right-0 pr-3 flex items-center"
                >
                  {showConfirmPassword ? (
                    <EyeOff className="h-5 w-5 text-white/50 hover:text-white/70 transition-colors" />
                  ) : (
                    <Eye className="h-5 w-5 text-white/50 hover:text-white/70 transition-colors" />
                  )}
                </button>
              </div>

              {/* Password Match Indicator */}
              {formData.confirmPassword && (
                <div className="flex items-center space-x-2 mt-2">
                  {isPasswordMatch ? (
                    <>
                      <Check className="h-4 w-4 text-green-400" />
                      <span className="text-xs text-green-400">
                        Mật khẩu khớp
                      </span>
                    </>
                  ) : (
                    <>
                      <AlertCircle className="h-4 w-4 text-red-400" />
                      <span className="text-xs text-red-400">
                        Mật khẩu không khớp
                      </span>
                    </>
                  )}
                </div>
              )}
            </div>

            {/* Submit Button */}
            <button
              type="submit"
              disabled={!isFormValid}
              className="w-full bg-gradient-to-r from-yellow-400 to-orange-500 text-white py-3 rounded-xl font-semibold hover:from-yellow-500 hover:to-orange-600 transform hover:scale-105 transition-all shadow-lg disabled:opacity-50 disabled:cursor-not-allowed disabled:transform-none"
            >
              Đặt lại mật khẩu
            </button>

            {/* Additional Info */}
            <div className="bg-white/5 rounded-xl p-4 border border-white/10">
              <div className="flex items-start space-x-3">
                <AlertCircle className="h-5 w-5 text-yellow-400 mt-0.5 flex-shrink-0" />
                <div className="text-sm text-white/80">
                  <p className="font-medium mb-1">Lưu ý bảo mật:</p>
                  <ul className="space-y-1 text-xs text-white/70">
                    <li>• Email phải là địa chỉ email đã đăng ký</li>
                    <li>• Mật khẩu mới phải khác mật khẩu cũ</li>
                    <li>• Đảm bảo mật khẩu đủ mạnh để bảo vệ tài khoản</li>
                    <li>• Không chia sẻ mật khẩu với người khác</li>
                  </ul>
                </div>
              </div>
            </div>

            {/* Back to Login Link */}
            <div className="text-center">
              <p className="text-white/70">
                Nhớ mật khẩu rồi?{" "}
                <Link
                  to="/auth/login"
                  className="text-yellow-300 hover:text-yellow-200 font-semibold transition-colors"
                >
                  Đăng nhập ngay
                </Link>
              </p>
            </div>
          </div>
        </form>
      </div>
    </div>
  );
}
