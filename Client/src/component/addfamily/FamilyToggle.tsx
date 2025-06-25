import React, { useState } from "react";
import InputType from "../inputype/InputType";

type Props = {
  onClose: () => void;
  onSubmit: (name: string, code: string) => void;
};

export default function FamilyToggle({onClose, onSubmit }: Props) {
  const [name, setName] = useState("");
  const [code, setCode] = useState("");


  const handleSubmit = (e: React.FormEvent) => {
    e.preventDefault();
    if (name.trim() && code.trim()) {
      onSubmit(name, code);
      setName("");
      setCode("");
    }
  };

  return (
    <div className="fixed inset-0 z-50 flex items-center justify-center bg-black/70">
      <div className="bg-white/10 backdrop-blur-lg border border-white/20 rounded-3xl p-4 sm:p-6 md:p-8 shadow-2xl max-w-lg w-full relative">
        <button
          className="absolute top-3 right-3 text-blue-200 hover:text-white text-2xl"
          onClick={onClose}
          aria-label="Đóng"
        >
          ×
        </button>
        <div className="text-center mb-6">
          <div className="w-20 h-20 bg-blue-500/20 rounded-full flex items-center justify-center mx-auto mb-4">
            <span className="text-4xl">🏠</span>
          </div>
          <h2 className="text-2xl font-bold text-white mb-2">
            Tạo gia đình mới
          </h2>
          <p className="text-blue-100 text-base">
            Điền thông tin để khởi tạo nhóm gia đình
          </p>
        </div>
        <form onSubmit={handleSubmit} className="space-y-5">
          <div>
            <label className="block text-blue-100 mb-1">Tên gia đình</label>
            <InputType
              type="text"
              value={name}
              onChange={(e) => setName(e)}
              className="w-full bg-white/20 border border-white/30 rounded-xl px-4 py-3 text-white placeholder-blue-200 focus:outline-none focus:ring-2 focus:ring-blue-400"
              placeholder="Nhập tên gia đình"
              required
            />
          </div>
          <div>
            <label className="block text-blue-100 mb-1">Mã gia đình</label>
            <InputType
              type="text"
              value={code}
              onChange={(e) => setCode(e)}
              className="w-full bg-white/20 border border-white/30 rounded-xl px-4 py-3 text-white placeholder-blue-200 focus:outline-none focus:ring-2 focus:ring-blue-400"
              placeholder="Nhập mã gia đình"
              required
            />
          </div>
          <button
            type="submit"
            className="w-full bg-blue-500 hover:bg-blue-600 text-white font-semibold py-3 rounded-xl transition-colors duration-300"
          >
            Tạo mới
          </button>
        </form>
      </div>
    </div>
  );
}
