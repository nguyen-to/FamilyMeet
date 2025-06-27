// pages/Home.tsx
import { useState } from "react";
import NoFamilyJoined from "../../component/nofamilyjoined/NoFamilyJoined";
import FamilyList, { type Family } from "../../component/familylist/FamilyList";
import { ChevronRight } from "lucide-react";

export default function PageMainUser() {
  const [addFamily, setAddFamily] = useState(true);
  const [families, setFamilies] = useState<Family[]>([
    { id: "1", name: "Gia đình Nguyễn", members: 4, role: "Chủ hộ" },
    { id: "2", name: "Gia đình Trần", members: 3, role: "Thành viên" },
    { id: "3", name: "Gia đình Lê", members: 5, role: "Thành viên" },
  ]);

  const handleEnterFamily = (familyId: string) => {
    console.log("Chuyển sang giao diện nhóm:", familyId);
    // TODO: điều hướng đến trang dashboard riêng cho nhóm
  };
  const HandleOnclickBack = () => {
    setAddFamily(!addFamily);
  };
  const HandleOnAddFamily = () => {
    setAddFamily(!addFamily);
  };
  return (
    <div className="bg-gradient-to-b from-blue-700 via-blue-800 to-purple-900 min-h-screen">
      {(!addFamily || families.length === 0) && (
        <div className="pt-6 pl-6">
          <button
            onClick={HandleOnclickBack}
            className="flex items-center text-white hover:text-blue-300 transition-colors"
          >
            <ChevronRight className="w-5 h-5 rotate-180 mr-2" />
            <span>Quay lại</span>
          </button>
        </div>
      )}
      <div className="text-center mb-8 pt-8">
        <h1 className="text-4xl font-bold text-white mb-2">
          Chào mừng bạn! 👋
        </h1>
        <p className="text-blue-100 text-lg">
          Quản lý gia đình của bạn một cách dễ dàng
        </p>
      </div>
      {families.length > 0 && addFamily ? (
        <FamilyList
          onClickAddFamily={HandleOnAddFamily}
          families={families}
          onEnterFamily={handleEnterFamily}
        />
      ) : (
        <NoFamilyJoined />
      )}
      <div className="text-center mt-8 text-blue-200">
        <p className="text-sm">
          © 2025 Family Manager. Kết nối gia đình, chia sẻ yêu thương.
        </p>
      </div>
    </div>
  );
}
