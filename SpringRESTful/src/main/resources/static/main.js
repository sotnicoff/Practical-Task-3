const tableBody = document.getElementById('table-body');
const userTableBody = document.getElementById('renderUserInfo')
const navBar = document.getElementById('nav-bar');
const addUserForm = document.getElementById('addUserForm');
const deleteUserForm = document.getElementById('deleteUserForm');
const editUserForm = document.getElementById('editUserForm');
const url = 'http://localhost:8080/api/users';

// Get - Render navigation bar with authorized user data
// Method: GET
const renderNavBar = user => {
    let temp = '';
    temp += `
            <span class="navbar-brand mb-0 h1">${user.email}</span>
            <span class="navbar-brand mb-0 h1">with roles: </span>
            <span class="navbar-brand mb-0 h1">${user.roles.map(role => role.name === "ROLE_USER" ? "USER" : "ADMIN")}</span>
        `;
    navBar.innerHTML = temp;
}

fetch('http://localhost:8080/api/auth', {
    method: "GET",
    headers: {
        'Content-Type': 'application/json'
    }
})
    .then(res => res.json())
    .then(data => renderNavBar(data))

function tableData() {
    let table = $('#tableUsers tbody');
    $('#table-body').empty();

    fetch(url)
        .then(res => res.json())
        .then(data => {
            data.forEach(user => {
                let tableRows = `$(
                <tr>
                    <td>${user.id}</td>
                    <td>${user.username}</td>
                    <td>${user.firstName}</td>
                    <td>${user.lastName}</td>
                    <td>${user.email}</td>
                    <td>${user.roles.map(role => role.name === 'ROLE_USER' ? 'USER' : 'ADMIN')}</td>
                    <td>
                       <button type="button" data-userid="${user.id}" data-action="edit" class="btn btn-info"
                        data-toggle="modal" data-target="modal" id="edit-user" data-id="${user.id}">Edit</button>
                   </td>
                   <td>
                       <button type="button" class="btn btn-danger" id="delete-user" data-action="delete"
                       data-id="${user.id}" data-target="modal">Delete</button>
                    </td>
                </tr>)`;
                table.append(tableRows);
            })
        })
}
tableData();

const on = (element, event, selector, handler) => {
    element.addEventListener(event, e => {
        if (e.target.closest(selector)) {
            handler(e)
        }
    })
}

function refreshTable() {

}

// Get - Render user data
// Method: GET
const renderUserInfo = user => {
    let temp = '';
    temp += `
        <tr>
            <td>${user.id}</td>
            <td>${user.username}</td>
            <td>${user.firstName}</td>
            <td>${user.lastName}</td>
            <td>${user.email}</td>
            <td>${user.roles.map(role => role.name === "ROLE_USER" ? "USER" : "ADMIN")}</td>
        </tr>`;
    userTableBody.innerHTML = temp;
}

fetch('http://localhost:8080/api/auth', {
    method: "GET",
    headers: {
        'Content-Type': 'application/json'
    }
})
    .then(res => res.json())
    .then(data => renderUserInfo(data))

// Edit - Update user
// Method: PUT
editUserForm.addEventListener('submit', e => {
    e.preventDefault()
    fetch(url, {
        method: 'PUT',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({
            id: document.getElementById('id-val').value,
            username: document.getElementById('username-val').value,
            firstName: document.getElementById('firstName-val').value,
            lastName: document.getElementById('lastName-val').value,
            email: document.getElementById('email-val').value,
            password: document.getElementById('password-val').value,
            roles: [
                document.getElementById('roles-val').value
            ]
        })
    })
        .then(res => res.json())
        .then(res => tableData())

    $("#modalEdit").modal('hide');
})


on(document, 'click', '#edit-user', e => {
    const userInfo = e.target.parentNode.parentNode;
    console.log(userInfo);
    document.getElementById('id-val').value = userInfo.children[0].innerHTML
    document.getElementById('username-val').value = userInfo.children[1].innerHTML
    document.getElementById('firstName-val').value = userInfo.children[2].innerHTML
    document.getElementById('lastName-val').value = userInfo.children[3].innerHTML
    document.getElementById('email-val').value = userInfo.children[4].innerHTML
    $('#modalEdit').modal('show')
})

// Delete - Remove user
// Method: DELETE
deleteUserForm.addEventListener('submit', e => {
    e.preventDefault()
    let id = document.getElementById('deleteId').value;
    fetch(`${url}/${id}`, {
        method: 'DELETE'
    })
        .then(() => tableData())

    $("#modalDelete").modal('hide');
})

on(document, 'click', '#delete-user', e => {
    const userInfo = e.target.parentNode.parentNode;
    document.getElementById('deleteId').value = userInfo.children[0].innerHTML
    document.getElementById('deleteUsername').value = userInfo.children[1].innerHTML
    document.getElementById('deleteFirstName').value = userInfo.children[2].innerHTML
    document.getElementById('deleteLastName').value = userInfo.children[3].innerHTML
    document.getElementById('deleteEmail').value = userInfo.children[4].innerHTML
    document.getElementById('deleteRoles').value = userInfo.children[5].innerHTML
    $('#modalDelete').modal('show')
})

// Post - Create new user
// Method: POST
addUserForm.addEventListener('submit', (e) => {
    e.preventDefault();
    fetch(url, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({
            username: document.getElementById('addUsername').value,
            firstName: document.getElementById('addFirstName').value,
            lastName: document.getElementById('addLastName').value,
            email: document.getElementById('addEmail').value,
            password: document.getElementById('addPassword').value,
            roles: [
                document.getElementById('addRoles').value
            ]
        }),

    })
        .then(res => res.json())
        .then(() => tableData())
    $('#list-tab').trigger('click');
})
