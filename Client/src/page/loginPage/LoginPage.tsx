import { useState } from "react";
import { Users, Eye, EyeOff, Mail, Lock, ArrowLeft } from "lucide-react";
import { Link } from "@tanstack/react-router";
import BackgrondDecoration from "../../component/backgrounddecoration";
import SocialAuth from "../../component/socialauth/SocialAuth";

export default function FamilyMeetLogin() {
  const [showPassword, setShowPassword] = useState(false);
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const [rememberMe, setRememberMe] = useState(false);

  const handleSubmit = () => {
    // Handle login logic here
    console.log("Login attempt:", { email, password, rememberMe });
  };

  return (
    <div className="min-h-screen bg-gradient-to-br from-blue-600 via-purple-600 to-indigo-800 flex items-center justify-center p-4">
      <BackgrondDecoration />

      {/* Login Container */}
      <div className="relative w-full max-w-md">
        {/* Back Button */}
        <button className="absolute -top-16 left-0 flex items-center space-x-2 text-white/80 hover:text-white transition-colors">
          <ArrowLeft className="h-5 w-5" />
          <Link to="/">Về Trang Chủ</Link>
        </button>

        {/* Login Card */}
        <form className="bg-white/10 backdrop-blur-lg rounded-3xl p-8 shadow-2xl border border-white/20">
          {/* Header */}
          <div className="text-center mb-8">
            <div className="flex justify-center mb-4">
              <div className="bg-gradient-to-r from-yellow-400 to-orange-500 p-3 rounded-2xl">
                <Users className="h-10 w-10 text-white" />
              </div>
            </div>
            <h1 className="text-3xl font-bold text-white mb-2">
              Chào Mừng Trở Lại!
            </h1>
            <p className="text-white/70">Đăng nhập để kết nối với gia đình</p>
          </div>

          {/* Login Form */}
          <div className="space-y-6">
            {/* Email Field */}
            <div className="space-y-2">
              <label className="text-white/90 text-sm font-medium">Email</label>
              <div className="relative">
                <div className="absolute inset-y-0 left-0 pl-3 flex items-center pointer-events-none">
                  <Mail className="h-5 w-5 text-white/50" />
                </div>
                <input
                  type="email"
                  value={email}
                  onChange={(e) => setEmail(e.target.value)}
                  className="w-full pl-10 pr-4 py-3 bg-white/10 border border-white/20 rounded-xl text-white placeholder-white/50 focus:outline-none focus:ring-2 focus:ring-yellow-400 focus:border-transparent transition-all"
                  placeholder="Nhập email của bạn"
                  required
                />
              </div>
            </div>

            {/* Password Field */}
            <div className="space-y-2">
              <label className="text-white/90 text-sm font-medium">
                Mật khẩu
              </label>
              <div className="relative">
                <div className="absolute inset-y-0 left-0 pl-3 flex items-center pointer-events-none">
                  <Lock className="h-5 w-5 text-white/50" />
                </div>
                <input
                  type={showPassword ? "text" : "password"}
                  value={password}
                  onChange={(e) => setPassword(e.target.value)}
                  className="w-full pl-10 pr-12 py-3 bg-white/10 border border-white/20 rounded-xl text-white placeholder-white/50 focus:outline-none focus:ring-2 focus:ring-yellow-400 focus:border-transparent transition-all"
                  placeholder="Nhập mật khẩu"
                  required
                />
                <button
                  type="button"
                  onClick={() => setShowPassword(!showPassword)}
                  className="absolute inset-y-0 right-0 pr-3 flex items-center"
                >
                  {showPassword ? (
                    <EyeOff className="h-5 w-5 text-white/50 hover:text-white/70 transition-colors" />
                  ) : (
                    <Eye className="h-5 w-5 text-white/50 hover:text-white/70 transition-colors" />
                  )}
                </button>
              </div>
            </div>

            {/* Remember Me & Forgot Password */}
            <div className="flex items-center justify-between">
              <label className="flex items-center">
                <input
                  type="checkbox"
                  checked={rememberMe}
                  onChange={(e) => setRememberMe(e.target.checked)}
                  className="w-4 h-4 text-yellow-400 bg-white/10 border-white/20 rounded focus:ring-yellow-400 focus:ring-2"
                />
                <span className="ml-2 text-sm text-white/80">
                  Ghi nhớ đăng nhập
                </span>
              </label>
              <a
                href="#"
                className="text-sm text-yellow-300 hover:text-yellow-200 transition-colors"
              >
                Quên mật khẩu?
              </a>
            </div>

            {/* Login Button */}
            <button
              onClick={handleSubmit}
              className="w-full bg-gradient-to-r from-yellow-400 to-orange-500 text-white py-3 rounded-xl font-semibold hover:from-yellow-500 hover:to-orange-600 transform hover:scale-105 transition-all shadow-lg"
            >
              Đăng Nhập
            </button>

            

            <SocialAuth buttonText={{google :"Google",facebook:"FaceBook"}} text="hoặc đăng nhập với" />

            {/* Sign Up Link */}
            <div className="text-center">
              <p className="text-white/70">
                Chưa có tài khoản?{" "}
                <Link
                  to="/auth/register"
                  className="text-yellow-300 hover:text-yellow-200 font-semibold transition-colors"
                >
                  Đăng ký ngay
                </Link>
              </p>
            </div>
          </div>
        </form>
      </div>
    </div>
  );
}
