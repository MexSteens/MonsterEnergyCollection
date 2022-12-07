import http from "../http-common";

const getAll = () => {
  return http.get("/comment");
};

const get = id => {
  return http.get(`/comment/${id}`);
};

const create = data => {
  return http.post("/comment", data);
};

const update = (id, data) => {
  return http.put(`/comment/${id}`, data);
};

const remove = id => {
  return http.delete(`/comment/${id}`);
};

const findByEnergyCan = (commentprops) => {
  return http.get(`/comment?energyCanId=${commentprops.energyCanId}&approved=${commentprops.approved}&deleted=${commentprops.deleted}`);
};

const findByUserAndEnergyCan = (commentprops) => {
  return http.get(`/comment?energyCanId=${commentprops.energyCanId}&userId=${commentprops.userId}`);
};

const findByApproval = () => {
  return http.get(`/comment?approved=0`);
};

const updateByApproval = (id) => {
  return http.put(`/comment/approve/${id}`)
};

const TutorialService = {
  getAll,
  get,
  create,
  update,
  remove,
  findByUserAndEnergyCan,
  findByEnergyCan,
  findByApproval,
  updateByApproval
};

export default TutorialService;