// components/NoFamilyJoined.tsx
import { ChevronRight, Home, Key, Link } from "lucide-react";
import { useState } from "react";
import FamilyToggle from "../addfamily/FamilyToggle";

export default function NoFamilyJoined() {
  const [inviteCode, setInviteCode] = useState("");
    const [familyToggle, setFamilyToggle] = useState(false);
  const handleJoinViaLink = () => {
    alert("Đang mở tính năng tham gia qua link...");
  };

  const handleCreateFamily = () => {
    setFamilyToggle(true);
  };
  const handleInviteCode = () => {
    if (inviteCode.trim()) {
      alert(`Đang tham gia với mã: ${inviteCode}`);
    } else {
      alert("Vui lòng nhập mã mời");
    }
  };
  const HandleSubmitAddFamily = (name: string, code: string) => {
    alert(`Đã tạo gia đình mới: ${name} với mã: ${code}`);
    setFamilyToggle(false);
  }
  return (
    <div className="bg-white/10 backdrop-blur-lg border border-white/20 rounded-3xl p-4 sm:p-6 md:p-8 shadow-2xl max-w-lg w-full mx-auto">
      {/* Thông báo */}
      <div className="text-center mb-8">
        <div className="w-20 h-20 bg-red-500/20 rounded-full flex items-center justify-center mx-auto mb-4">
          <span className="text-4xl">🛑</span>
        </div>
        <h2 className="text-2xl font-bold text-white mb-2">
          Bạn chưa tham gia nhóm gia đình nào
        </h2>
        <p className="text-blue-100">
          Hãy tham gia hoặc tạo một nhóm gia đình để bắt đầu
        </p>
      </div>

      {/* Các lựa chọn */}
      <div className="space-y-4">
        {/* Nhập mã mời */}
        <div className="bg-white/10 backdrop-blur-sm border border-white/20 rounded-2xl p-6 hover:bg-white/15 transition-all duration-300">
          <div className="flex items-center mb-4">
            <div className="w-12 h-12 bg-blue-500/30 rounded-full flex items-center justify-center mr-4">
              <Key className="w-6 h-6 text-white" />
            </div>
            <h3 className="text-xl font-semibold text-white">Nhập mã mời</h3>
          </div>
          <div className="flex gap-3">
            <input
              type="text"
              placeholder="Nhập mã mời..."
              value={inviteCode}
              onChange={(e) => setInviteCode(e.target.value)}
              className="flex-1 bg-white/20 border border-white/30 rounded-xl px-4 py-3 text-white placeholder-blue-200 focus:outline-none focus:ring-2 focus:ring-blue-400"
            />
            <button
              onClick={handleInviteCode}
              className="bg-blue-500 hover:bg-blue-600 text-white px-6 py-3 rounded-xl font-medium transition-colors duration-300"
            >
              Tham gia
            </button>
          </div>
        </div>

        {/* Tham gia qua link */}
        <div
          className="bg-white/10 backdrop-blur-sm border border-white/20 rounded-2xl p-6 hover:bg-white/15 transition-all duration-300 cursor-pointer"
          onClick={handleJoinViaLink}
        >
          <div className="flex items-center justify-between">
            <div className="flex items-center">
              <div className="w-12 h-12 bg-green-500/30 rounded-full flex items-center justify-center mr-4">
                <Link className="w-6 h-6 text-white" />
              </div>
              <div>
                <h3 className="text-xl font-semibold text-white">
                  Tham gia qua link
                </h3>
                <p className="text-blue-200 text-sm">
                  Sử dụng link mời từ người khác
                </p>
              </div>
            </div>
            <ChevronRight className="w-6 h-6 text-white" />
          </div>
        </div>

        {/* Tạo gia đình mới */}
        <div
          className="bg-white/10 backdrop-blur-sm border border-white/20 rounded-2xl p-6 hover:bg-white/15 transition-all duration-300 cursor-pointer"
          onClick={handleCreateFamily}
        >
          <div className="flex items-center justify-between">
            <div className="flex items-center">
              <div className="w-12 h-12 bg-purple-500/30 rounded-full flex items-center justify-center mr-4">
                <Home className="w-6 h-6 text-white" />
              </div>
              <div>
                <h3 className="text-xl font-semibold text-white">
                  Tạo gia đình mới
                </h3>
                <p className="text-blue-200 text-sm">
                  Trở thành người khởi tạo nhóm gia đình
                </p>
              </div>
            </div>
            <ChevronRight className="w-6 h-6 text-white" />
          </div>
        </div>
      </div>
      {
        familyToggle && <FamilyToggle onSubmit={HandleSubmitAddFamily} onClose={() => setFamilyToggle(false)} />
      }
    </div>
  );
}
