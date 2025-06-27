// components/FamilyDashboard/MainContent.tsx
import React from 'react';
import { 
  Hash, 
  Volume2, 
  Video, 
  Bell, 
  Search, 
  Phone, 
  Users,
  Paperclip,
  Smile,
  Send,
  Crown,
  Shield,
  Menu,
} from 'lucide-react';
import type { Channel, Family, Message } from '../dashboard/FamilyDashboard';

interface MainContentProps {
  channel?: Channel;
  messages: Message[];
  family: Family;
  message: string;
  onMessageChange: (message: string) => void;
  onSendMessage: () => void;
  onVoiceCall: () => void;
  onToggleSidebar: () => void;
  onToggleMembers: () => void;
}

const MainContent: React.FC<MainContentProps> = ({
  channel,
  messages,
  family,
  message,
  onMessageChange,
  onSendMessage,
  onVoiceCall,
  onToggleSidebar,
  onToggleMembers
}) => {
  const getChannelIcon = (type: string) => {
    switch (type) {
      case 'voice': return <Volume2 className="w-4 h-4" />;
      case 'video': return <Video className="w-4 h-4" />;
      default: return <Hash className="w-4 h-4" />;
    }
  };

  const getRoleIcon = (role: string) => {
    switch (role) {
      case 'owner': return <Crown className="w-3 h-3 text-yellow-500" />;
      case 'admin': return <Shield className="w-3 h-3 text-blue-500" />;
      default: return null;
    }
  };

  const getStatusColor = (status: string) => {
    switch (status) {
      case 'online': return 'bg-green-500';
      case 'away': return 'bg-yellow-500';
      case 'busy': return 'bg-red-500';
      default: return 'bg-gray-500';
    }
  };

  const handleKeyPress = (e: React.KeyboardEvent) => {
    if (e.key === 'Enter' && !e.shiftKey) {
      e.preventDefault();
      onSendMessage();
    }
  };

  return (
    <div className="flex flex-col h-full">
      {/* Channel Header - Responsive */}
      <div className="h-14 lg:h-16 bg-gray-800 border-b border-gray-700 flex items-center justify-between px-3 lg:px-6 flex-shrink-0">
        <div className="flex items-center space-x-2 lg:space-x-3 min-w-0 flex-1">
          {/* Mobile Menu Button */}
          <button
            className="lg:hidden p-2 rounded-md hover:bg-gray-700 transition-colors touch-manipulation"
            onClick={onToggleSidebar}
          >
            <Menu className="w-5 h-5 text-gray-400" />
          </button>

          {getChannelIcon(channel?.type || 'text')}
          <h2 className="text-base lg:text-lg font-medium truncate">
            {channel?.name || 'general'}
          </h2>
          <span className="text-xs lg:text-sm text-gray-400 hidden sm:inline truncate">
            {channel?.is_general ? 'Kênh chính' : 'Kênh riêng'}
          </span>
        </div>
        
        <div className="flex items-center space-x-1 lg:space-x-3 flex-shrink-0">
          {/* Desktop Actions */}
          <div className="hidden lg:flex items-center space-x-3">
            <button className="p-2 rounded-md hover:bg-gray-700 transition-colors">
              <Bell className="w-5 h-5 text-gray-400" />
            </button>
            <button className="p-2 rounded-md hover:bg-gray-700 transition-colors">
              <Search className="w-5 h-5 text-gray-400" />
            </button>
            <button 
              className="p-2 rounded-md hover:bg-gray-700 transition-colors"
              onClick={onVoiceCall}
            >
              <Phone className="w-5 h-5 text-gray-400" />
            </button>
            <button className="p-2 rounded-md hover:bg-gray-700 transition-colors">
              <Video className="w-5 h-5 text-gray-400" />
            </button>
          </div>

          {/* Mobile/Tablet Actions */}
          <button 
            className="lg:hidden p-2 rounded-md hover:bg-gray-700 transition-colors touch-manipulation"
            onClick={onVoiceCall}
          >
            <Phone className="w-4 h-4 text-gray-400" />
          </button>
          
          <button
            className="hidden md:block lg:hidden p-2 rounded-md hover:bg-gray-700 transition-colors touch-manipulation"
            onClick={onToggleMembers}
          >
            <Users className="w-4 h-4 text-gray-400" />
          </button>
        </div>
      </div>

      {/* Messages Area - Scrollable */}
      <div className="flex-1 overflow-y-auto p-3 lg:p-4 space-y-3 lg:space-y-4">
        {/* Welcome Message - Responsive */}
        <div className="bg-gradient-to-r from-purple-600 to-pink-600 p-3 lg:p-4 rounded-lg">
          <h3 className="text-base lg:text-lg font-semibold mb-2">
            🎉 Chào mừng đến với {family.name}!
          </h3>
          <p className="text-purple-100 mb-3 text-sm lg:text-base">
            Bạn đã tham gia thành công vào family. Hãy bắt đầu trò chuyện với mọi người nhé!
          </p>
          <div className="flex flex-col sm:flex-row sm:items-center space-y-2 sm:space-y-0 sm:space-x-2 text-sm">
            <span className="bg-purple-500 px-2 py-1 rounded inline-block">Mã mời:</span>
            <code className="bg-black bg-opacity-30 px-2 py-1 rounded font-mono break-all">
              {family.invite_code}
            </code>
          </div>
        </div>

        {/* Messages - Responsive */}
        {messages.map((msg) => (
          <div key={msg.id} className="flex items-start space-x-2 lg:space-x-3 hover:bg-gray-800 p-2 rounded-md">
            <div className="relative flex-shrink-0">
              <div className="w-8 h-8 lg:w-10 lg:h-10 bg-gradient-to-br from-blue-500 to-purple-500 rounded-full flex items-center justify-center font-medium text-sm">
                {msg.user.avatar_url ? (
                  <img src={msg.user.avatar_url} alt={msg.user.display_name} className="w-full h-full rounded-full object-cover" />
                ) : (
                  msg.user.display_name.charAt(0)
                )}
              </div>
              <div className={`absolute -bottom-1 -right-1 w-3 h-3 lg:w-4 lg:h-4 rounded-full border-2 border-gray-800 ${getStatusColor(msg.user.status)}`}></div>
            </div>
            <div className="flex-1 min-w-0">
              <div className="flex items-center space-x-2 mb-1 flex-wrap">
                <span className="font-medium text-sm lg:text-base">{msg.user.display_name}</span>
                {getRoleIcon(msg.user.role)}
                <span className="text-xs text-gray-400">{msg.timestamp}</span>
              </div>
              <p className="text-gray-300 text-sm lg:text-base break-words">{msg.content}</p>
            </div>
          </div>
        ))}
      </div>

      {/* Message Input - Responsive */}
      <div className="p-3 lg:p-4 bg-gray-800 border-t border-gray-700 flex-shrink-0">
        <div className="flex items-end space-x-2 lg:space-x-3">
          <button className="p-2 rounded-md hover:bg-gray-700 transition-colors touch-manipulation flex-shrink-0">
            <Paperclip className="w-4 h-4 lg:w-5 lg:h-5 text-gray-400" />
          </button>
          
          <div className="flex-1 relative">
            <textarea
              value={message}
              onChange={(e) => onMessageChange(e.target.value)}
              onKeyPress={handleKeyPress}
              placeholder={`Nhắn tin trong #${channel?.name || 'general'}`}
              rows={1}
              className="w-full bg-gray-700 text-white rounded-lg px-3 lg:px-4 py-2 pr-10 lg:pr-12 focus:outline-none focus:ring-2 focus:ring-purple-500 resize-none text-sm lg:text-base"
              style={{ minHeight: '40px', maxHeight: '120px' }}
            />
            <button className="absolute right-2 top-1/2 transform -translate-y-1/2 p-1 rounded-md hover:bg-gray-600 transition-colors touch-manipulation">
              <Smile className="w-4 h-4 lg:w-5 lg:h-5 text-gray-400" />
            </button>
          </div>
          
          <button
            onClick={onSendMessage}
            disabled={!message.trim()}
            className="p-2 rounded-md bg-purple-600 hover:bg-purple-700 disabled:bg-gray-600 disabled:cursor-not-allowed transition-colors touch-manipulation flex-shrink-0"
          >
            <Send className="w-4 h-4 lg:w-5 lg:h-5" />
          </button>
        </div>
      </div>
    </div>
  );
};

export default MainContent;
