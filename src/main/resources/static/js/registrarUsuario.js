async function registrarUsuario(){
    //! Recuperar valores del formulario
    const email = document.getElementById('email').value;
    const nombre = document.getElementById('nombre').value;
    const apellidos = document.getElementById('apellidos').value;
    const telefono = document.getElementById('telefono').value;
    const password = document.getElementById('password').value;

    try{
        const response = await fetch('http://localhost:8080/usuarios/registrar', {
            method:'POST',
            headers:{
                'Content-Type':'application/json'
            },
            body: JSON.stringify({
                email: email,
                nombre: nombre,
                apellidos: apellidos,
                telefono: telefono,
                password: password
            })
        })

    } catch(Exception){
        console.error('Ha ocurrido un error al obtener los usuarios:', error.message);
    }
}