import request from "@/utils/request";

export interface UserLoginDTO {
  account: string;
  password: string;
}

export interface UserCreateDTO {
  userName: string;
  password: string;
  email: string;
  phone: string | null;
}
export const UserLogin = (data: UserLoginDTO) => {
  return request.post("/user/login", data);
};

export const UserRegister = (data: UserCreateDTO) => {
  return request.post("/user/register", data);
};
