import { Users, Heart, Video, User } from "lucide-react";
export const BackgrondDecoration = () => {
  return (
    <div className="absolute inset-0 overflow-hidden">
      <div className="absolute top-10 left-10 animate-bounce">
        <div className="bg-white/5 backdrop-blur-sm p-4 rounded-full">
          <Heart className="h-8 w-8 text-pink-300" />
        </div>
      </div>
      <div className="absolute top-32 right-10 animate-pulse">
        <div className="bg-white/5 backdrop-blur-sm p-4 rounded-full">
          <Video className="h-8 w-8 text-blue-300" />
        </div>
      </div>
      <div className="absolute bottom-10 left-16 animate-bounce delay-1000">
        <div className="bg-white/5 backdrop-blur-sm p-3 rounded-full">
          <Users className="h-6 w-6 text-purple-300" />
        </div>
      </div>
      <div className="absolute bottom-32 right-16 animate-pulse delay-500">
        <div className="bg-white/5 backdrop-blur-sm p-3 rounded-full">
          <Heart className="h-6 w-6 text-yellow-300" />
        </div>
      </div>
      <div className="absolute top-1/2 left-4 animate-bounce delay-700">
        <div className="bg-white/5 backdrop-blur-sm p-2 rounded-full">
          <User className="h-5 w-5 text-green-300" />
        </div>
      </div>
    </div>
  );
};
