// components/FamilyDashboard/Sidebar.tsx
import React from 'react';
import { 
  Hash, 
  Volume2, 
  Video, 
  Settings, 
  UserPlus, 
  Calendar,
  Mic,
  MicOff,
  PhoneCall
} from 'lucide-react';
import type { Channel, Family } from '../../page/dashboard/FamilyDashboard';

interface SidebarProps {
  family: Family;
  channels: Channel[];
  activeChannel: string;
  isInCall: boolean;
  isMuted: boolean;
  onChannelSelect: (channelId: string) => void;
  onCallToggle: () => void;
  onMuteToggle: () => void;
}

const Sidebar: React.FC<SidebarProps> = ({
  family,
  channels,
  activeChannel,
  isInCall,
  isMuted,
  onChannelSelect,
  onCallToggle,
  onMuteToggle
}) => {
  const getChannelIcon = (type: string) => {
    switch (type) {
      case 'voice': return <Volume2 className="w-4 h-4" />;
      case 'video': return <Video className="w-4 h-4" />;
      default: return <Hash className="w-4 h-4" />;
    }
  };

  const textChannels = channels.filter(c => c.type === 'text');
  const voiceChannels = channels.filter(c => c.type !== 'text');

  return (
    <div className="w-full h-full bg-gray-800 flex flex-col">
      {/* Family Header - Responsive */}
      <div className="p-3 lg:p-4 border-b border-gray-700 flex-shrink-0">
        <div className="flex items-center justify-between">
          <div className="flex items-center space-x-2 lg:space-x-3 min-w-0">
            <div className="w-8 h-8 lg:w-10 lg:h-10 bg-gradient-to-br from-purple-500 to-pink-500 rounded-lg flex items-center justify-center font-bold text-sm lg:text-lg flex-shrink-0">
              {family.avatar_url ? (
                <img src={family.avatar_url} alt={family.name} className="w-full h-full rounded-lg object-cover" />
              ) : (
                family.name.charAt(0)
              )}
            </div>
            <div className="min-w-0 flex-1">
              <h2 className="font-semibold text-white text-sm lg:text-base truncate">{family.name}</h2>
              <p className="text-xs text-gray-400 truncate">
                {family.current_members}/{family.max_members} thành viên
              </p>
            </div>
          </div>
          <Settings className="w-4 h-4 lg:w-5 lg:h-5 text-gray-400 hover:text-white cursor-pointer flex-shrink-0" />
        </div>
      </div>

      {/* Channels - Scrollable */}
      <div className="flex-1 overflow-y-auto">
        {/* Text Channels */}
        <div className="p-3 lg:p-4">
          <div className="flex items-center justify-between mb-2 lg:mb-3">
            <h3 className="text-xs font-semibold text-gray-400 uppercase tracking-wide">
              Kênh Trò Chuyện
            </h3>
            <UserPlus className="w-3 h-3 lg:w-4 lg:h-4 text-gray-400 hover:text-white cursor-pointer" />
          </div>
          
          <div className="space-y-1">
            {textChannels.map((channel) => (
              <div
                key={channel.id}
                className={`
                  flex items-center justify-between p-2 rounded-md cursor-pointer transition-colors
                  ${activeChannel === channel.id ? 'bg-gray-600' : 'hover:bg-gray-700'}
                  active:bg-gray-500 touch-manipulation
                `}
                onClick={() => onChannelSelect(channel.id)}
              >
                <div className="flex items-center space-x-2 lg:space-x-3 min-w-0 flex-1">
                  {getChannelIcon(channel.type)}
                  <span className="text-sm truncate">{channel.name}</span>
                </div>
                {channel.unread_count && channel.unread_count > 0 && (
                  <span className="bg-red-500 text-xs px-2 py-1 rounded-full min-w-[20px] text-center flex-shrink-0">
                    {channel.unread_count}
                  </span>
                )}
              </div>
            ))}
          </div>
        </div>

        {/* Voice Channels */}
        <div className="p-3 lg:p-4 border-t border-gray-700">
          <div className="flex items-center justify-between mb-2 lg:mb-3">
            <h3 className="text-xs font-semibold text-gray-400 uppercase tracking-wide">
              Kênh Thoại
            </h3>
          </div>
          
          <div className="space-y-1">
            {voiceChannels.map((channel) => (
              <div
                key={channel.id}
                className="flex items-center justify-between p-2 rounded-md cursor-pointer hover:bg-gray-700 transition-colors active:bg-gray-500 touch-manipulation"
                onClick={() => onChannelSelect(channel.id)}
              >
                <div className="flex items-center space-x-2 lg:space-x-3 min-w-0 flex-1">
                  {getChannelIcon(channel.type)}
                  <span className="text-sm truncate">{channel.name}</span>
                </div>
                <div className="flex items-center space-x-2 flex-shrink-0">
                  <div className="w-2 h-2 bg-green-500 rounded-full"></div>
                  <span className="text-xs text-gray-400">2</span>
                </div>
              </div>
            ))}
          </div>
        </div>

        {/* Events Section - Hidden on small screens */}
        <div className="p-3 lg:p-4 border-t border-gray-700 hidden sm:block">
          <div className="flex items-center justify-between mb-2 lg:mb-3">
            <h3 className="text-xs font-semibold text-gray-400 uppercase tracking-wide">
              Sự Kiện
            </h3>
            <Calendar className="w-3 h-3 lg:w-4 lg:h-4 text-gray-400 hover:text-white cursor-pointer" />
          </div>
          
          <div className="bg-gradient-to-r from-blue-600 to-purple-600 p-3 rounded-md">
            <h4 className="text-sm font-medium">Sinh nhật Mẹ</h4>
            <p className="text-xs text-blue-100">Ngày mai - 15:00</p>
            <p className="text-xs text-blue-200 mt-1">5 người tham gia</p>
          </div>
        </div>
      </div>

      {/* Voice Call Status - Sticky bottom */}
      {isInCall && (
        <div className="p-3 lg:p-4 bg-green-600 border-t border-green-500 flex-shrink-0">
          <div className="flex items-center justify-between">
            <div className="flex items-center space-x-2 min-w-0 flex-1">
              <div className="w-2 h-2 bg-green-300 rounded-full animate-pulse flex-shrink-0"></div>
              <span className="text-sm font-medium truncate">Voice Chat</span>
            </div>
            <div className="flex items-center space-x-2 flex-shrink-0">
              <button
                onClick={onMuteToggle}
                className={`p-1 lg:p-2 rounded touch-manipulation ${isMuted ? 'bg-red-500' : 'bg-green-700'}`}
              >
                {isMuted ? <MicOff className="w-3 h-3 lg:w-4 lg:h-4" /> : <Mic className="w-3 h-3 lg:w-4 lg:h-4" />}
              </button>
              <button
                onClick={onCallToggle}
                className="p-1 lg:p-2 rounded bg-red-500 hover:bg-red-600 touch-manipulation"
              >
                <PhoneCall className="w-3 h-3 lg:w-4 lg:h-4" />
              </button>
            </div>
          </div>
        </div>
      )}
    </div>
  );
};

export default Sidebar;
