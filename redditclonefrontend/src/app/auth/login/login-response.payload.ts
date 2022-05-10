export interface LoginResponse {
  authToken: string;
  refreshToken: string;
  expiresIn: Date;
  username: string;
}
