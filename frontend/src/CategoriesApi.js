let topics = []

fetch(process.env.REACT_APP_API_URL + "/category", {mode:'cors'})
          .then(response => response.json())
          .then(data => topics = data )
          .catch(err => { console.log(err) })

export function getResource({ resourceId, topicId }) {
  return topics
    .find(({ id }) => id === topicId)
    .resources.find(({ id }) => id === resourceId);
}

export function getTopic(topicId) {
  return topics.find(({ id }) => id === topicId);
}

export function getTopics() {
  return topics;
}