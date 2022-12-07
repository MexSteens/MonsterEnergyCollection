import http from "../http-common";

const getAll = () => {
  return http.get("/review");
};

const get = id => {
  return http.get(`/review/${id}`);
};

const create = data => {
  return http.post("/review", data);
};

const update = (id, data) => {
  return http.put(`/review/${id}`, data);
};

const remove = id => {
  return http.delete(`/review/${id}`);
};

const findByUserAndEnergyCan = (props) => {
  return http.get(`/review?energyCanId=${props.energyCanId}&userId=${props.userId}`);
}

const TutorialService = {
  getAll,
  get,
  create,
  update,
  remove,
  findByUserAndEnergyCan
};

export default TutorialService;