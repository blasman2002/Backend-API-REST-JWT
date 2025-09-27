let url = 'api/v1/usuarios/perfil';

function buscarPorNombre(nombre) {
    fetch(`${url}/${nombre}`)
        .then(response => response.json())
        .then(data => console.log(data))
        .catch(error => console.error("Error al buscar usuario:", error));
}

buscarPorNombre('nico7');