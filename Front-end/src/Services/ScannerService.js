import http from "../http-common";

const getAll = () => {
  return http.get("/can");
};

const get = id => {
  return http.get(`/can/${id}`);
};

const create = data => {
  return http.post("/can", data);
};

const update = (id, data) => {
  return http.put(`/can/${id}`, data);
};

const remove = id => {
  return http.delete(`/can/${id}`);
};

// const findByTitle = title => {
//   return http.get(`/can?title=${title}`);
// };

const TutorialService = {
  getAll,
  get,
  create,
  update,
  remove,
//   findByTitle
};

export default TutorialService;