// components/FamilyDashboard/MembersSidebar.tsx
import React from 'react';
import { Crown, Shield } from 'lucide-react';
import type { User } from '../dashboard/FamilyDashboard';

interface MembersSidebarProps {
  members: User[];
}

const MembersSidebar: React.FC<MembersSidebarProps> = ({ members }) => {
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

  const onlineMembers = members.filter(m => m.status === 'online');
  const offlineMembers = members.filter(m => m.status !== 'online');

  return (
    <div className="w-full h-full bg-gray-800 border-l border-gray-700 flex flex-col">
      {/* Header */}
      <div className="p-3 lg:p-4 border-b border-gray-700 flex-shrink-0">
        <h3 className="font-semibold text-white text-sm lg:text-base">
          Thành viên — {members.length}
        </h3>
      </div>
      
      {/* Members List - Scrollable */}
      <div className="flex-1 overflow-y-auto">
        {/* Online Members */}
        {onlineMembers.length > 0 && (
          <div className="p-3 lg:p-4">
            <h4 className="text-xs font-semibold text-gray-400 uppercase tracking-wide mb-2 lg:mb-3">
              Đang hoạt động — {onlineMembers.length}
            </h4>
            
            <div className="space-y-1">
              {onlineMembers.map((member) => (
                <div 
                  key={member.id} 
                  className="flex items-center space-x-2 lg:space-x-3 p-2 rounded-md hover:bg-gray-700 cursor-pointer transition-colors active:bg-gray-600 touch-manipulation"
                >
                  <div className="relative flex-shrink-0">
                    <div className="w-6 h-6 lg:w-8 lg:h-8 bg-gradient-to-br from-green-500 to-blue-500 rounded-full flex items-center justify-center font-medium text-xs lg:text-sm">
                      {member.avatar_url ? (
                        <img src={member.avatar_url} alt={member.display_name} className="w-full h-full rounded-full object-cover" />
                      ) : (
                        member.display_name.charAt(0)
                      )}
                    </div>
                    <div className={`absolute -bottom-1 -right-1 w-2 h-2 lg:w-3 lg:h-3 rounded-full border-2 border-gray-800 ${getStatusColor(member.status)}`}></div>
                  </div>
                  <div className="flex-1 min-w-0">
                    <div className="flex items-center space-x-1">
                      <span className="text-xs lg:text-sm font-medium truncate">{member.display_name}</span>
                      {getRoleIcon(member.role)}
                    </div>
                  </div>
                </div>
              ))}
            </div>
          </div>
        )}

        {/* Offline Members */}
        {offlineMembers.length > 0 && (
          <div className="p-3 lg:p-4 border-t border-gray-700">
            <h4 className="text-xs font-semibold text-gray-400 uppercase tracking-wide mb-2 lg:mb-3">
              Offline — {offlineMembers.length}
            </h4>
            
            <div className="space-y-1">
              {offlineMembers.map((member) => (
                <div 
                  key={member.id} 
                  className="flex items-center space-x-2 lg:space-x-3 p-2 rounded-md hover:bg-gray-700 cursor-pointer opacity-60 transition-colors active:bg-gray-600 touch-manipulation"
                >
                  <div className="relative flex-shrink-0">
                    <div className="w-6 h-6 lg:w-8 lg:h-8 bg-gradient-to-br from-gray-500 to-gray-600 rounded-full flex items-center justify-center font-medium text-xs lg:text-sm">
                      {member.avatar_url ? (
                        <img src={member.avatar_url} alt={member.display_name} className="w-full h-full rounded-full object-cover" />
                      ) : (
                        member.display_name.charAt(0)
                      )}
                    </div>
                    <div className={`absolute -bottom-1 -right-1 w-2 h-2 lg:w-3 lg:h-3 rounded-full border-2 border-gray-800 ${getStatusColor(member.status)}`}></div>
                  </div>
                  <div className="flex-1 min-w-0">
                    <div className="flex items-center space-x-1">
                      <span className="text-xs lg:text-sm font-medium truncate">{member.display_name}</span>
                      {getRoleIcon(member.role)}
                    </div>
                  </div>
                </div>
              ))}
            </div>
          </div>
        )}
      </div>
    </div>
  );
};

export default MembersSidebar;
