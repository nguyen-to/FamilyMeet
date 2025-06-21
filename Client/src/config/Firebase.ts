import { initializeApp } from 'firebase/app';
import { getAuth, GoogleAuthProvider, FacebookAuthProvider } from 'firebase/auth';
const firebaseConfig = {
  apiKey: "AIzaSyCJuXAHHKee5O8x4o9RTv-1TZxqmSEJQ9I",
  authDomain: "familymeet-da1a8.firebaseapp.com",
  projectId: "familymeet-da1a8",
  storageBucket: "familymeet-da1a8.firebasestorage.app",
  messagingSenderId: "375638808240",
  appId: "1:375638808240:web:fcbdd893681926d7044d75",
  measurementId: "G-X16R8SP7TN"
};

// Khởi tạo Firebase
const app = initializeApp(firebaseConfig);

// Khởi tạo Firebase Authentication và lấy tham chiếu đến service
export const auth = getAuth(app);

// Cấu hình Google Provider
export const googleProvider = new GoogleAuthProvider();
googleProvider.setCustomParameters({
  prompt: 'select_account'
});

// Cấu hình Facebook Provider
export const facebookProvider = new FacebookAuthProvider();
facebookProvider.setCustomParameters({
  display: 'popup'
});

export default app;
