$(document).ready(async function() {
  try {
    const listUsers = await getAllUsers();
    setUsersInTable(listUsers);
    $('#dataTable').DataTable();
  } catch (error) {
    console.error('Ha ocurrido un error al obtener los usuarios:', error.message);
  }
});

async function getAllUsers() {
  try {
    const fetchResponse = await fetch('http://localhost:8080/usuarios/obtener/todos');

    if (!fetchResponse.ok) {
      throw new Error('Error en la solicitud: ' + fetchResponse.status);
    }

    const response = await fetchResponse.json();

    if (!Array.isArray(response)) {
      throw new Error('No se ha podido obtener la información de los usuarios');
    }

    return response;

  } catch (error) {
    throw new Error('Ha ocurrido un error al obtener los usuarios: ' + error.message);
  }
}

async function deleteUser(email) {
  try {
    alert("Eliminar usuario");
    if (!confirm("¿Estás seguro de que deseas eliminar este usuario?")){
      return;
    }

    const response = await fetch("http://localhost:8080/usuarios/eliminar/"+email, {
      method: 'DELETE',
      headers: {
        'Content-Type': 'application/json',
        'Authorization': 'Bearer ' + localStorage.token
      },
    });

    location.reload();
  } catch (e) {
    console.log("error", e);
  }
}

function setUsersInTable(listUsers) {
  const tableUsers = document.querySelector("#dataTable tbody");

  // Limpiamos el contenido existente en la tabla
  tableUsers.innerHTML = '';

  // Iteramos sobre la lista de usuarios y construimos dinámicamente las filas de la tabla
  listUsers.forEach((user, index) => {
    const trTemplate = `<tr>
      <td>${index + 1}</td>
      <td>${user.nombre}</td>
      <td>${user.email}</td>
      <td>
        <a href="#" class="btn btn-danger btn-circle btn-sm">
          <i onclick="deleteUser('${user.email}')" class="fas fa-trash"></i>
        </a>
      </td>
    </tr>`;

    // Agregamos la fila a la tabla
    tableUsers.innerHTML += trTemplate;
  });
}


