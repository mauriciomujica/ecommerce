const apiBaseUrl = 'http://localhost:8080';
let allProductos = [];
let allPedidos = [];

document.addEventListener('DOMContentLoaded', () => {
    loadCategorias();
    loadProductos();
    loadPedidos();
    document.getElementById('producto-form').addEventListener('submit', saveProducto);
    document.getElementById('pedido-form').addEventListener('submit', savePedido);
});

function showNotification(message, isError = false) {
    const notif = document.getElementById('notification');
    notif.textContent = message;
    notif.className = isError ? 'error' : 'success';
    notif.classList.remove('hidden');
    setTimeout(() => notif.classList.add('hidden'), 5000);
}

function showSection(section) {
    document.querySelectorAll('section').forEach(s => s.classList.remove('active'));
    document.getElementById('home').classList.remove('active');
    if (section) {
        document.getElementById(section).classList.add('active');
        if (section === 'productos') {
            loadProductos();
        } else if (section === 'pedidos') {
            loadPedidos();
        }
    }
}

function showForm(type) {
    document.getElementById(`${type}-form-container`).classList.remove('hidden');
    if (type === 'pedido') clearPedidoForm();
}

function hideForm(type) {
    document.getElementById(`${type}-form-container`).classList.add('hidden');
    document.getElementById(`${type}-form`).reset();
    if (type === 'producto') document.getElementById('producto-id').value = '';
}

async function loadCategorias() {
    const categorias = ['ALMACEN', 'BEBIDAS', 'CARNES', 'CONGELADOS', 'LACTEOS'];
    const subcategorias = {
        ALMACEN: ['HARINAS', 'PASTAS', 'ACEITES'],
        BEBIDAS: ['MINERALES', 'SABORIZADAS', 'GASEOSAS'],
        CARNES: ['CARNES', 'POLLOS', 'PESCADOS'],
        CONGELADOS: ['HAMBURGUESAS', 'HELADOS', 'NUGGETS'],
        LACTEOS: ['LECHES', 'YOGURES', 'QUESOS']
    };

    const catSelect = document.getElementById('producto-categoria');
    catSelect.innerHTML = '<option value="">Seleccionar categoría</option>';
    categorias.forEach(c => {
        catSelect.innerHTML += `<option value="${c}">${c}</option>`;
    });

    catSelect.onchange = () => {
        const subcatSelect = document.getElementById('producto-subcategoria');
        const selectedCat = catSelect.value;
        subcatSelect.innerHTML = '<option value="">Seleccionar subcategoría</option>';
        if (selectedCat && subcategorias[selectedCat]) {
            subcategorias[selectedCat].forEach(sc => {
                subcatSelect.innerHTML += `<option value="${sc}">${sc}</option>`;
            });
        }
    };
}

async function loadProductos() {
    const response = await fetch(`${apiBaseUrl}/productos?pageNumber=0&pageSize=100`);
    const data = await response.json();
    allProductos = data.content;
    renderProductos(allProductos);
}

function renderProductos(productos) {
    const tbody = document.querySelector('#productos-table tbody');
    tbody.innerHTML = '';
    productos.forEach(p => {
        tbody.innerHTML += `
            <tr>
                <td>${p.id}</td>
                <td>${p.nombre}</td>
                <td>${p.stock}</td>
                <td>$${p.precio}</td>
                <td>${p.categoria}</td>
                <td>${p.subcategoria}</td>
                <td>
                    <button onclick="editProducto(${p.id})">Actualizar</button>
                    <button onclick="deleteProducto(${p.id})">Eliminar</button>
                </td>
            </tr>
        `;
    });
}

async function searchProductos() {
    const searchTerm = document.getElementById('producto-search').value.trim();
    if (searchTerm) {
        const isId = /^\d+$/.test(searchTerm);
        if (isId) {
            try {
                const response = await fetch(`${apiBaseUrl}/productos/${searchTerm}`);
                if (!response.ok) {
                    const errorText = await response.text();
                    showNotification(errorText, true);
                    renderProductos([]);
                    return;
                }
                const p = await response.json();
                renderProductos([p]);
            } catch (e) {
                renderProductos([]);
            }
        } else {
            const response = await fetch(`${apiBaseUrl}/productos?pageNumber=0&pageSize=100&nombre=${encodeURIComponent(searchTerm)}`);
            const data = await response.json();
            renderProductos(data.content);
        }
    } else {
        renderProductos(allProductos);
    }
}

function sortProductos() {
    const field = document.getElementById('producto-sort-field').value;
    const order = document.getElementById('producto-sort-order').value;
    const sorted = [...allProductos].sort((a, b) => {
        let valA = a[field] || '';
        let valB = b[field] || '';
        if (['precio', 'stock', 'id'].includes(field)) {
            valA = a[field] || 0;
            valB = b[field] || 0;
            return order === 'asc' ? valA - valB : valB - valA;
        }
        return order === 'asc' 
            ? (valA > valB ? 1 : -1) 
            : (valA < valB ? 1 : -1);
    });
    renderProductos(sorted);
}

async function loadPedidos() {
    const response = await fetch(`${apiBaseUrl}/pedidos?pageNumber=0&pageSize=100`);
    const data = await response.json();
    allPedidos = data.content;
    renderPedidos(allPedidos);
}

function renderPedidos(pedidos) {
    const tbody = document.querySelector('#pedidos-table tbody');
    tbody.innerHTML = '';
    pedidos.forEach(p => {
        tbody.innerHTML += `
            <tr>
                <td>${p.id}</td>
                <td>
                    <table class="items-detail">
                        <thead>
                            <tr>
                                <th>Producto</th>
                                <th>Cant.</th>
                                <th>Precio</th>
                            </tr>
                        </thead>
                        <tbody>
                            ${p.itemsPedido.map(item => `
                                <tr>
                                    <td>${item.producto?.nombre || 'N/A'}</td>
                                    <td>${item.cantidad}</td>
                                    <td>$${item.precio}</td>
                                </tr>
                            `).join('')}
                        </tbody>
                    </table>
                </td>
                <td>$${p.precioFinalPedido}</td>
                <td>
                    <button onclick="deletePedido(${p.id})">Eliminar</button>
                </td>
            </tr>
        `;
    });
}

async function searchPedidos() {
    const searchId = document.getElementById('pedido-search').value;
    if (searchId) {
        try {
            const response = await fetch(`${apiBaseUrl}/pedidos/${searchId}`);
            if (!response.ok) {
                const errorText = await response.text();
                showNotification(errorText, true);
                renderPedidos([]);
                return;
            }
            const p = await response.json();
            renderPedidos([p]);
        } catch (e) {
            renderPedidos([]);
        }
    } else {
        renderPedidos(allPedidos);
    }
}

function sortPedidos() {
    const field = document.getElementById('pedido-sort-field').value;
    const order = document.getElementById('pedido-sort-order').value;
    const sorted = [...allPedidos].sort((a, b) => {
        let valA = a[field] || 0;
        let valB = b[field] || 0;
        if (order === 'asc') {
            return valA - valB;
        } else {
            return valB - valA;
        }
    });
    renderPedidos(sorted);
}

async function saveProducto(e) {
    e.preventDefault();
    const id = document.getElementById('producto-id').value;
    const producto = {
        nombre: document.getElementById('producto-nombre').value,
        stock: parseInt(document.getElementById('producto-stock').value),
        precio: parseFloat(document.getElementById('producto-precio').value),
        categoria: document.getElementById('producto-categoria').value,
        subcategoria: document.getElementById('producto-subcategoria').value
    };

    const url = id ? `${apiBaseUrl}/productos/${id}` : `${apiBaseUrl}/productos`;
    const method = id ? 'PATCH' : 'POST';

    try {
        const response = await fetch(url, {
            method,
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(producto)
        });
        if (!response.ok) {
            const errorText = await response.text();
            showNotification(errorText, true);
            return;
        }
        showNotification(id ? 'Producto actualizado' : 'Producto creado');
    } catch (err) {
        showNotification('Error al guardar producto', true);
        return;
    }

    hideForm('producto');
    loadProductos();
}

async function editProducto(id) {
    const response = await fetch(`${apiBaseUrl}/productos/${id}`);
    const p = await response.json();
    document.getElementById('producto-id').value = p.id;
    document.getElementById('producto-nombre').value = p.nombre;
    document.getElementById('producto-stock').value = p.stock;
    document.getElementById('producto-precio').value = p.precio;
    
    const catSelect = document.getElementById('producto-categoria');
    const subcatSelect = document.getElementById('producto-subcategoria');
    
    subcatSelect.innerHTML = '<option value="">Seleccionar subcategoría</option>';
    
    const subcategorias = {
        ALMACEN: ['HARINAS', 'PASTAS', 'ACEITES'],
        BEBIDAS: ['MINERALES', 'SABORIZADAS', 'GASEOSAS'],
        CARNES: ['CARNES', 'POLLOS', 'PESCADOS'],
        CONGELADOS: ['HAMBURGUESAS', 'HELADOS', 'NUGGETS'],
        LACTEOS: ['LECHES', 'YOGURES', 'QUESOS']
    };
    
    catSelect.value = p.categoria;
    subcategorias[p.categoria].forEach(sc => {
        subcatSelect.innerHTML += `<option value="${sc}">${sc}</option>`;
    });
    subcatSelect.value = p.subcategoria;
    
    showForm('producto');
}

async function deleteProducto(id) {
    await fetch(`${apiBaseUrl}/productos/${id}`, { method: 'DELETE' });
    loadProductos();
}

let pedidoItemCount = 0;

function clearPedidoForm() {
    document.getElementById('pedido-items').innerHTML = '';
    pedidoItemCount = 0;
    addItem();
}

function addItem() {
    const itemRow = document.createElement('div');
    itemRow.className = 'item-row';
    const removeBtn = pedidoItemCount > 0 ? '<button type="button" onclick="removeItem(this)">Eliminar</button>' : '';
    itemRow.innerHTML = `
        <input type="number" placeholder="ID Producto" class="item-id" required>
        <input type="number" placeholder="Cantidad" class="item-cantidad" required>
        ${removeBtn}
    `;
    document.getElementById('pedido-items').appendChild(itemRow);
    pedidoItemCount++;
}

function removeItem(button) {
    button.parentElement.remove();
    pedidoItemCount--;
}

async function savePedido(e) {
    e.preventDefault();
    const items = [];
    document.querySelectorAll('.item-row').forEach(row => {
        items.push({
            id: parseInt(row.querySelector('.item-id').value),
            cantidad: parseInt(row.querySelector('.item-cantidad').value)
        });
    });

    try {
        const response = await fetch(`${apiBaseUrl}/pedidos/nuevo`, {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify({ itemsPedido: items })
        });
        if (!response.ok) {
            const errorText = await response.text();
            showNotification(errorText, true);
            return;
        }
        showNotification('Pedido creado');
    } catch (err) {
        showNotification('Error al crear pedido', true);
        return;
    }

    hideForm('pedido');
    loadPedidos();
}

async function deletePedido(id) {
    await fetch(`${apiBaseUrl}/pedidos/${id}`, { method: 'DELETE' });
    loadPedidos();
}