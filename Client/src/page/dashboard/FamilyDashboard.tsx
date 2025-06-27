// components/FamilyDashboard/index.tsx
import React, { useState } from 'react';
import MainContent from '../maincontent/MainContent';
import MembersSidebar from '../membersidebar/MemberSidebar';
import Sidebar from '../../component/sidebar/Sidebar';

// Types giữ nguyên như cũ...
interface User {
  id: string;
  display_name: string;
  avatar_url?: string;
  status: 'online' | 'offline' | 'away' | 'busy';
  role: 'owner' | 'admin' | 'moderator' | 'member';
}

interface Channel {
  id: string;
  name: string;
  type: 'text' | 'voice' | 'video';
  is_general: boolean;
  unread_count?: number;
}

interface Message {
  id: string;
  user: User;
  content: string;
  timestamp: string;
  message_type: 'text' | 'system';
}

interface Family {
  id: string;
  name: string;
  avatar_url?: string;
  invite_code: string;
  current_members: number;
  max_members: number;
}

const FamilyDashboard: React.FC = () => {
  const [activeChannel, setActiveChannel] = useState<string>('general');
  const [message, setMessage] = useState<string>('');
  const [isInCall, setIsInCall] = useState<boolean>(false);
  const [isMuted, setIsMuted] = useState<boolean>(false);
  
  // Responsive states
  const [showSidebar, setShowSidebar] = useState<boolean>(false);
  const [showMembers, setShowMembers] = useState<boolean>(false);

  // Mock data giữ nguyên...
  const family: Family = {
    id: '1',
    name: 'Gia đình Nguyễn',
    avatar_url: undefined,
    invite_code: 'ABC123XYZ',
    current_members: 5,
    max_members: 20
  };

  const channels: Channel[] = [
    { id: 'general', name: 'general', type: 'text', is_general: true, unread_count: 0 },
    { id: 'announcements', name: 'thông báo', type: 'text', is_general: false, unread_count: 2 },
    { id: 'photos', name: 'ảnh gia đình', type: 'text', is_general: false },
    { id: 'voice-general', name: 'Voice Chat', type: 'voice', is_general: false },
    { id: 'video-room', name: 'Video Room', type: 'video', is_general: false }
  ];

  const members: User[] = [
    { id: '1', display_name: 'Bố', status: 'online', role: 'owner', avatar_url: undefined },
    { id: '2', display_name: 'Mẹ', status: 'online', role: 'admin', avatar_url: undefined },
    { id: '3', display_name: 'Anh Hai', status: 'away', role: 'member', avatar_url: undefined },
    { id: '4', display_name: 'Em Gái', status: 'online', role: 'member', avatar_url: undefined },
    { id: '5', display_name: 'Tôi', status: 'online', role: 'member', avatar_url: undefined }
  ];

  const messages: Message[] = [
    {
      id: '1',
      user: { id: '1', display_name: 'Bố', status: 'online', role: 'owner' },
      content: 'Chào mừng mọi người đến với gia đình! 🏠',
      timestamp: '10:30 AM',
      message_type: 'text'
    },
    {
      id: '2',
      user: { id: '5', display_name: 'Tôi', status: 'online', role: 'member' },
      content: 'Đã tham gia family thành công! 🎉',
      timestamp: '10:32 AM',
      message_type: 'system'
    },
    {
      id: '3',
      user: { id: '2', display_name: 'Mẹ', status: 'online', role: 'admin' },
      content: 'Hôm nay có gì vui không các con?',
      timestamp: '10:35 AM',
      message_type: 'text'
    }
  ];

  const handleSendMessage = () => {
    if (message.trim()) {
      console.log('Sending message:', message);
      setMessage('');
    }
  };

  const currentChannel = channels.find(c => c.id === activeChannel);

  return (
    <div className="flex h-screen bg-gray-900 text-white relative overflow-hidden">
      {/* Mobile Overlay */}
      {(showSidebar || showMembers) && (
        <div 
          className="fixed inset-0 bg-black bg-opacity-50 z-40 lg:hidden"
          onClick={() => {
            setShowSidebar(false);
            setShowMembers(false);
          }}
        />
      )}

      {/* Sidebar - Responsive */}
      <div className={`
        fixed lg:relative lg:translate-x-0 z-50 lg:z-0
        w-64 lg:w-64 md:w-72 sm:w-64
        h-full bg-gray-800 flex flex-col
        transform transition-transform duration-300 ease-in-out
        ${showSidebar ? 'translate-x-0' : '-translate-x-full lg:translate-x-0'}
      `}>
        <Sidebar
          family={family}
          channels={channels}
          activeChannel={activeChannel}
          isInCall={isInCall}
          isMuted={isMuted}
          onChannelSelect={(channelId) => {
            setActiveChannel(channelId);
            setShowSidebar(false); // Close sidebar on mobile after selection
          }}
          onCallToggle={() => setIsInCall(!isInCall)}
          onMuteToggle={() => setIsMuted(!isMuted)}
        />
      </div>
      
      {/* Main Content - Always visible */}
      <div className="flex-1 flex flex-col min-w-0">
        <MainContent
          channel={currentChannel}
          messages={messages}
          family={family}
          message={message}
          onMessageChange={setMessage}
          onSendMessage={handleSendMessage}
          onVoiceCall={() => setIsInCall(true)}
          onToggleSidebar={() => setShowSidebar(!showSidebar)}
          onToggleMembers={() => setShowMembers(!showMembers)}
        />
      </div>
      
      {/* Members Sidebar - Responsive */}
      <div className={`
        fixed lg:relative lg:translate-x-0 right-0 z-50 lg:z-0
        w-64 lg:w-64 md:w-72 sm:w-64
        h-full bg-gray-800 border-l border-gray-700
        transform transition-transform duration-300 ease-in-out
        ${showMembers ? 'translate-x-0' : 'translate-x-full lg:translate-x-0'}
        hidden md:block
      `}>
        <MembersSidebar members={members} />
      </div>
    </div>
  );
};

export default FamilyDashboard;
export type { User, Channel, Message, Family };
