// components/FamilyList.tsx
import { ChevronRight, UserPlus, Users } from "lucide-react";

export type Family = {
  id: string;
  name: string;
  members: number;
  role: string;
};

type Props = {
  families: Family[];
  onEnterFamily: (id: string) => void;
  onClickAddFamily?: () => void;
};

export default function FamilyList({ families,onClickAddFamily }: Props) {
  const handleJoinFamily = (familyName: string) => {
    alert(`Đang vào nhóm: ${familyName}`);
  };
  return (
    <div className="bg-white/10 backdrop-blur-lg border border-white/20 rounded-3xl p-4 sm:p-6 md:p-8 shadow-2xl max-w-lg w-full mx-auto">
      {/* Header */}
      <div className="text-center mb-8">
        <div className="w-20 h-20 bg-green-500/20 rounded-full flex items-center justify-center mx-auto mb-4">
          <span className="text-4xl">👨‍👩‍👧‍👦</span>
        </div>
        <h2 className="text-2xl font-bold text-white mb-2">
          Các nhóm gia đình của bạn
        </h2>
        <p className="text-blue-100">Chọn một nhóm để tiếp tục</p>
      </div>

      {/* Danh sách gia đình */}
      <div className="space-y-4 mb-6">
        {families.map((family) => (
          <div
            key={family.id}
            className="bg-white/10 backdrop-blur-sm border border-white/20 rounded-2xl p-6 hover:bg-white/15 transition-all duration-300"
          >
            <div className="flex items-center justify-between">
              <div className="flex items-center">
                <div className="w-14 h-14 bg-gradient-to-br from-blue-400 to-purple-400 rounded-full flex items-center justify-center mr-4">
                  <Users className="w-7 h-7 text-white" />
                </div>
                <div>
                  <h3 className="text-xl font-semibold w-[170px] text-white mb-1 line-clamp-1">
                    {family.name}
                  </h3>
                  <div className="flex items-center gap-4 text-blue-200 text-sm">
                    <span>{family.members} thành viên</span>
                    <span>•</span>
                    <span>Vai trò: {family.role}</span>
                  </div>
                </div>
              </div>
              <button
                onClick={() => handleJoinFamily(family.name)}
                className="bg-blue-500 hover:bg-blue-600 text-white px-6 py-3 rounded-xl font-medium transition-colors duration-300 flex items-center gap-2"
              >
                Vào nhóm
                <ChevronRight className="w-4 h-4" />
              </button>
            </div>
          </div>
        ))}
      </div>

      {/* Nút tham gia thêm gia đình */}
      <div className="border-t border-white/20 pt-6">
        <button
        onClick={onClickAddFamily}
        className="w-full bg-white/10 backdrop-blur-sm border border-white/20 rounded-2xl p-4 hover:bg-white/15 transition-all duration-300 flex items-center justify-center gap-3 text-white">
          <UserPlus className="w-5 h-5" />
          Tham gia thêm gia đình khác
        </button>
      </div>
    </div>
  );
}
