export type AuthFromResponse = {
    accessToken: string;
    fullName: string;
    email: string;
    picture: string;
    deviceId: string;
}
export type AuthRegisterRequest = {
    fullName: string;
    email: string;
    phone: string;
    password: string;
}
export type ResetPasswordForm = {
  email: string;
  newPassword: string;
};