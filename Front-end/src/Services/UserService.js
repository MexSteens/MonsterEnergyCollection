import http from "../http-common";

const getAll = () => {
  return http.get("/user");
};

const get = id => {
  return http.get(`/user/${id}`);
};

const create = data => {
  return http.post("/user", data);
};

const update = (id, data) => {
  return http.put(`/user/${id}`, data);
};

const remove = id => {
  return http.delete(`/user/${id}`);
};

const login = (data) => {
  return http.post("/login", data);
};

const updateUserRole = (id, data) => {
  return http.put(`/user/role/${id}`, data);
};

const findByUsername = (username) => {
  return http.get(`/users?search=username:${username}`)
}


const UserService = {
  getAll,
  get,
  create,
  update,
  remove,
  login,
  updateUserRole,
  findByUsername
};

export default UserService;