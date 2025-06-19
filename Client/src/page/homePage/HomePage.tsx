import { useState } from "react";
import {
  Users,
  Video,
  Calendar,
  Heart,
  Menu,
  X,
  Star,
  Shield,
} from "lucide-react";
import { Link } from "@tanstack/react-router";

export default function FamilyMeetHomepage() {
  const [isMenuOpen, setIsMenuOpen] = useState(false);

  return (
      <div className="min-h-screen  bg-gradient-to-br from-blue-600 via-purple-600 to-indigo-800">
        {/* Header */}
        <header className="relative z-50">
          <nav className="container mx-auto px-4 sm:px-6 py-4">
            <div className="flex items-center justify-between">
              <div className="flex items-center space-x-2">
                <div className="bg-white/20 backdrop-blur-sm p-2 rounded-lg">
                  <Users className="h-8 w-8 text-white" />
                </div>
                <span className="text-2xl font-bold text-white">
                  FamilyMeet
                </span>
              </div>

              {/* Desktop Menu */}
              <div className="hidden md:flex items-center space-x-8">
                <a
                  href="#"
                  className="text-white/90 hover:text-white transition-colors"
                >
                  Trang Chủ
                </a>
                <a
                  href="#"
                  className="text-white/90 hover:text-white transition-colors"
                >
                  Tính Năng
                </a>
                <a
                  href="#"
                  className="text-white/90 hover:text-white transition-colors"
                >
                  Về Chúng Tôi
                </a>
                <a
                  href="#"
                  className="text-white/90 hover:text-white transition-colors"
                >
                  Liên Hệ
                </a>
                <button className="bg-white/20 backdrop-blur-sm text-white px-6 py-2 rounded-full hover:bg-white/30 transition-all">
                  <Link to="/auth/login">Đăng Nhập</Link>
                </button>
              </div>

              {/* Mobile Menu Button */}
              <button
                onClick={() => setIsMenuOpen(!isMenuOpen)}
                className="md:hidden text-white"
              >
                {isMenuOpen ? (
                  <X className="h-6 w-6" />
                ) : (
                  <Menu className="h-6 w-6" />
                )}
              </button>
            </div>

            {/* Mobile Menu */}
            {isMenuOpen && (
              <div className="md:hidden mt-4 bg-white/10 backdrop-blur-sm rounded-lg p-4">
                <div className="flex flex-col space-y-4">
                  <a
                    href="#"
                    className="text-white/90 hover:text-white transition-colors"
                  >
                    Trang Chủ
                  </a>
                  <a
                    href="#"
                    className="text-white/90 hover:text-white transition-colors"
                  >
                    Tính Năng
                  </a>
                  <a
                    href="#"
                    className="text-white/90 hover:text-white transition-colors"
                  >
                    Về Chúng Tôi
                  </a>
                  <a
                    href="#"
                    className="text-white/90 hover:text-white transition-colors"
                  >
                    Liên Hệ
                  </a>
                  <button className="bg-white/20 backdrop-blur-sm text-white px-6 py-2 rounded-full hover:bg-white/30 transition-all w-full">
                    Đăng Nhập
                  </button>
                </div>
              </div>
            )}
          </nav>
        </header>

        {/* Hero Section */}
        <section className="container mx-auto px-4 sm:px-6 py-12 sm:py-20 text-center">
          <div className="max-w-4xl mx-auto">
            <h1 className="text-4xl sm:text-5xl md:text-7xl font-bold text-white mb-4 sm:mb-6 leading-tight">
              Kết Nối
              <span className="bg-gradient-to-r from-yellow-300 to-orange-400 bg-clip-text text-transparent">
                {" "}
                Gia Đình
              </span>
            </h1>
            <p className="text-lg sm:text-xl md:text-2xl text-white/90 mb-6 sm:mb-8 leading-relaxed px-4 sm:px-0">
              Nền tảng video call hiện đại giúp các thành viên trong gia đình
              luôn gần gũi, dù ở bất kỳ đâu trên thế giới
            </p>
            <div className="flex flex-col sm:flex-row gap-4 justify-center px-4 sm:px-0">
              <button className="bg-gradient-to-r from-yellow-400 to-orange-500 text-white px-8 py-4 rounded-full text-lg font-semibold hover:from-yellow-500 hover:to-orange-600 transform hover:scale-105 transition-all shadow-lg">
                Bắt Đầu Ngay
              </button>
              <button className="bg-white/20 backdrop-blur-sm text-white px-8 py-4 rounded-full text-lg font-semibold hover:bg-white/30 transition-all border border-white/30">
                Xem Demo
              </button>
            </div>
          </div>

          {/* Floating Elements */}
          <div className="absolute top-20 left-10 animate-bounce">
            <div className="bg-white/10 backdrop-blur-sm p-3 rounded-full">
              <Heart className="h-6 w-6 text-pink-300" />
            </div>
          </div>
          <div className="absolute top-40 right-10 animate-pulse">
            <div className="bg-white/10 backdrop-blur-sm p-3 rounded-full">
              <Video className="h-6 w-6 text-blue-300" />
            </div>
          </div>
        </section>

        {/* Features Section */}
        <section className="container mx-auto px-4 sm:px-6 py-12 sm:py-20">
          <h2 className="text-3xl sm:text-4xl font-bold text-white text-center mb-8 sm:mb-12">
            Tính Năng Nổi Bật
          </h2>
          <div className="grid sm:grid-cols-2 md:grid-cols-3 gap-6 sm:gap-8">
            <div className="bg-white/10 backdrop-blur-sm rounded-2xl p-8 hover:bg-white/20 transition-all transform hover:scale-105">
              <div className="bg-gradient-to-r from-blue-400 to-purple-500 p-3 rounded-lg w-fit mb-4">
                <Video className="h-8 w-8 text-white" />
              </div>
              <h3 className="text-2xl font-bold text-white mb-4">
                Video Call HD
              </h3>
              <p className="text-white/80 leading-relaxed">
                Chất lượng video và âm thanh crystal clear, mang lại trải nghiệm
                gần gũi như thật
              </p>
            </div>

            <div className="bg-white/10 backdrop-blur-sm rounded-2xl p-8 hover:bg-white/20 transition-all transform hover:scale-105">
              <div className="bg-gradient-to-r from-green-400 to-blue-500 p-3 rounded-lg w-fit mb-4">
                <Calendar className="h-8 w-8 text-white" />
              </div>
              <h3 className="text-2xl font-bold text-white mb-4">
                Lịch Họp Mặt
              </h3>
              <p className="text-white/80 leading-relaxed">
                Lên lịch cuộc gọi gia đình, nhắc nhở tự động để không bỏ lỡ
                những khoảnh khắc quý giá
              </p>
            </div>

            <div className="bg-white/10 backdrop-blur-sm rounded-2xl p-8 hover:bg-white/20 transition-all transform hover:scale-105">
              <div className="bg-gradient-to-r from-purple-400 to-pink-500 p-3 rounded-lg w-fit mb-4">
                <Shield className="h-8 w-8 text-white" />
              </div>
              <h3 className="text-2xl font-bold text-white mb-4">
                Bảo Mật Tuyệt Đối
              </h3>
              <p className="text-white/80 leading-relaxed">
                Mã hóa end-to-end đảm bảo những khoảnh khắc riêng tư của gia
                đình luôn được bảo vệ
              </p>
            </div>
          </div>
        </section>

        {/* Stats Section */}
        <section className="container mx-auto px-4 sm:px-6 py-12 sm:py-20">
          <div className="bg-white/10 backdrop-blur-sm rounded-3xl p-6 sm:p-12">
            <div className="grid sm:grid-cols-2 md:grid-cols-3 gap-6 sm:gap-8 text-center">
              <div>
                <div className="text-4xl font-bold text-white mb-2">10K+</div>
                <div className="text-white/80">Gia Đình Tin Dùng</div>
              </div>
              <div>
                <div className="text-4xl font-bold text-white mb-2">1M+</div>
                <div className="text-white/80">Cuộc Gọi Thành Công</div>
              </div>
              <div>
                <div className="text-4xl font-bold text-white mb-2">99.9%</div>
                <div className="text-white/80">Thời Gian Hoạt Động</div>
              </div>
            </div>
          </div>
        </section>

        {/* Testimonials */}
        <section className="container mx-auto px-4 sm:px-6 py-12 sm:py-20">
          <h2 className="text-3xl sm:text-4xl font-bold text-white text-center mb-8 sm:mb-12">
            Khách Hàng Nói Gì
          </h2>
          <div className="grid sm:grid-cols-2 gap-6 sm:gap-8">
            <div className="bg-white/10 backdrop-blur-sm rounded-2xl p-8">
              <div className="flex items-center mb-4">
                {[...Array(5)].map((_, i) => (
                  <Star
                    key={i}
                    className="h-5 w-5 text-yellow-400 fill-current"
                  />
                ))}
              </div>
              <p className="text-white/90 mb-4 leading-relaxed">
                "FamilyMeet đã giúp gia đình tôi kết nối gần gũi hơn mặc dù
                chúng tôi sống xa nhau. Chất lượng video rất tốt và dễ sử dụng."
              </p>
              <div className="text-white font-semibold">- Chị Lan, Hà Nội</div>
            </div>

            <div className="bg-white/10 backdrop-blur-sm rounded-2xl p-8">
              <div className="flex items-center mb-4">
                {[...Array(5)].map((_, i) => (
                  <Star
                    key={i}
                    className="h-5 w-5 text-yellow-400 fill-current"
                  />
                ))}
              </div>
              <p className="text-white/90 mb-4 leading-relaxed">
                "Tính năng lịch họp mặt rất tiện lợi. Bây giờ cả nhà đều có thể
                tham gia cuộc gọi vào những thời điểm phù hợp nhất."
              </p>
              <div className="text-white font-semibold">- Anh Minh, TP.HCM</div>
            </div>
          </div>
        </section>

        {/* CTA Section */}
        <section className="container mx-auto px-4 sm:px-6 py-12 sm:py-20 text-center">
          <div className="bg-gradient-to-r from-white/10 to-white/5 backdrop-blur-sm rounded-3xl p-6 sm:p-12">
            <h2 className="text-3xl sm:text-4xl font-bold text-white mb-4 sm:mb-6">
              Sẵn Sàng Kết Nối Gia Đình?
            </h2>
            <p className="text-xl text-white/90 mb-8">
              Tham gia cùng hàng nghìn gia đình đã tin tưởng FamilyMeet
            </p>
            <button className="bg-gradient-to-r from-yellow-400 to-orange-500 text-white px-12 py-4 rounded-full text-xl font-semibold hover:from-yellow-500 hover:to-orange-600 transform hover:scale-105 transition-all shadow-lg">
              Dùng Thử Miễn Phí
            </button>
          </div>
        </section>

        {/* Footer */}
        <footer className="container mx-auto px-4 sm:px-6 py-8 sm:py-12 border-t border-white/20">
          <div className="grid sm:grid-cols-2 md:grid-cols-4 gap-8">
            <div>
              <div className="flex items-center space-x-2 mb-4">
                <div className="bg-white/20 backdrop-blur-sm p-2 rounded-lg">
                  <Users className="h-6 w-6 text-white" />
                </div>
                <span className="text-xl font-bold text-white">FamilyMeet</span>
              </div>
              <p className="text-white/70">
                Kết nối gia đình, gắn kết yêu thương
              </p>
            </div>

            <div>
              <h4 className="text-white font-semibold mb-4">Sản Phẩm</h4>
              <div className="space-y-2">
                <a
                  href="#"
                  className="block text-white/70 hover:text-white transition-colors"
                >
                  Video Call
                </a>
                <a
                  href="#"
                  className="block text-white/70 hover:text-white transition-colors"
                >
                  Lịch Họp
                </a>
                <a
                  href="#"
                  className="block text-white/70 hover:text-white transition-colors"
                >
                  Chia Sẻ File
                </a>
              </div>
            </div>

            <div>
              <h4 className="text-white font-semibold mb-4">Hỗ Trợ</h4>
              <div className="space-y-2">
                <a
                  href="#"
                  className="block text-white/70 hover:text-white transition-colors"
                >
                  Trung Tâm Trợ Giúp
                </a>
                <a
                  href="#"
                  className="block text-white/70 hover:text-white transition-colors"
                >
                  Liên Hệ
                </a>
                <a
                  href="#"
                  className="block text-white/70 hover:text-white transition-colors"
                >
                  FAQ
                </a>
              </div>
            </div>

            <div>
              <h4 className="text-white font-semibold mb-4">Công Ty</h4>
              <div className="space-y-2">
                <a
                  href="#"
                  className="block text-white/70 hover:text-white transition-colors"
                >
                  Về Chúng Tôi
                </a>
                <a
                  href="#"
                  className="block text-white/70 hover:text-white transition-colors"
                >
                  Tuyển Dụng
                </a>
                <a
                  href="#"
                  className="block text-white/70 hover:text-white transition-colors"
                >
                  Chính Sách
                </a>
              </div>
            </div>
          </div>

          <div className="border-t border-white/20 mt-8 pt-8 text-center">
            <p className="text-white/70">
              © 2025 FamilyMeet. Tất cả quyền được bảo lưu.
            </p>
          </div>
        </footer>
      </div>
  );
}
