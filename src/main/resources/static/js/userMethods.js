const ENDPOINT = "http://localhost:8080";

export const deleteUser = async (email) => {
    try {
        const response = fetch(ENDPOINT + "/usuarios/eliminar/" + email);
        const responseJson = await fetchResponse.json();

        console.log(responseJson)

    } catch (e) {
        console.log("error")
    }
}