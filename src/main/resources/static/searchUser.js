let url = 'api/v1/users/profile';

function searchProfileId(id) {
    fetch(`api/v1/users/${id}/profile`)
        .then(response => response.json())
        .then(data => console.log(data))
        .catch(error => console.error("Error al buscar usuario:", error));
}

searchProfileId('2');