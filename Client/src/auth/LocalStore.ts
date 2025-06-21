import type { UserResponse } from "../formstyle/UserForm";

export const  GetAccessToken = () => localStorage.getItem("access_token");
export const SaveAccessToken = (body: string) => localStorage.setItem("access_token",body);
export const GetProfileUser = () => {
    const result = localStorage.getItem('user');
    return result ? JSON.parse(result) : null;
};
export const SaveDeviceId = (body: string) => localStorage.setItem('deviceId', body);
export const GetDeviceId = () => localStorage.getItem('deviceId');
export const SaveProfileUser = (body: UserResponse) => localStorage.setItem('user',JSON.stringify(body));
export const ClearAll = () => {
    localStorage.removeItem("user");
    localStorage.removeItem("access_token");
    localStorage.removeItem("deviceId");
}
