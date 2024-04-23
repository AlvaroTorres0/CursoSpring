async function iniciarSesion() {
    const email = document.getElementById('email').value;
    const password = document.getElementById('password').value;

    try {
        const response = await fetch('http://localhost:8080/auth/login', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({
                email: email,
                password: password
            })
        });

        // Verificar si la solicitud fue exitosa (código de estado en el rango 200-299)
        if (!response.ok) {
            throw new Error(`Error de red: ${response.status}`);
        }

        const data = await response.json();

        localStorage.token = data.token;
        localStorage.email = email;
    } catch (error) {
        console.error('Error:', error);
    }
}

/*function iniciarSesion(){
    const email = document.getElementById('email').value;
    const password = document.getElementById('password').value;

    fetch('http://localhost:8080/auth/login', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({
            email: email,
            password: password
        })
    })
        .then(response => {
            if (!response.ok) {
                throw new Error('Error: ' + response.status);
            }
            return response.json();
        })
        .then(data => {
            console.log('Success:', data);
            localStorage.setItem('sesion token', data.token);
        })

        .catch(error => {
            console.error('Error:', error);
        })
        .finally(() => {
            console.log('Siempre se ejecuta');
        });
}
*/