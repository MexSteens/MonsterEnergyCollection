import http from "../http-common";

const getAll = () => {
  return http.get("/scannedEnergyCan");
};

const get = id => {
  return http.get(`/scannedEnergyCan/${id}`);
};

const create = data => {
  return http.post("/scannedEnergyCan", data);
};

const update = (id, data) => {
  return http.put(`/scannedEnergyCan/${id}`, data);
};

const remove = id => {
  return http.delete(`/scannedEnergyCan/${id}`);
};

const findByUser = (props) => {
  return http.get(`/scannedEnergyCan?userId=${props.userId}`);
}

const TutorialService = {
  getAll,
  get,
  create,
  update,
  remove,
  findByUser
};

export default TutorialService;