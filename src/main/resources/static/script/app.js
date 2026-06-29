const apiBaseUrl = 'http://localhost:8080';

document.addEventListener('DOMContentLoaded', () => {
    loadCategorias();
    loadProductos();
    loadPedidos();
    document.getElementById('producto-form').addEventListener('submit', saveProducto);
    document.getElementById('pedido-form').addEventListener('submit', savePedido);
});

function showSection(section) {
    document.querySelectorAll('section').forEach(s => s.classList.remove('active'));
    document.getElementById('home').classList.remove('active');
    if (section) document.getElementById(section).classList.add('active');
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
    const tbody = document.querySelector('#productos-table tbody');
    tbody.innerHTML = '';
    data.content.forEach(p => {
        tbody.innerHTML += `
            <tr>
                <td>${p.id}</td>
                <td>${p.nombre}</td>
                <td>${p.stock}</td>
                <td>${p.precio}</td>
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

async function loadPedidos() {
    const response = await fetch(`${apiBaseUrl}/pedidos?pageNumber=0&pageSize=100`);
    const data = await response.json();
    const tbody = document.querySelector('#pedidos-table tbody');
    tbody.innerHTML = '';
    data.content.forEach(p => {
        tbody.innerHTML += `
            <tr>
                <td>${p.id}</td>
                <td>${JSON.stringify(p.itemsPedido)}</td>
                <td>${p.precioFinalPedido}</td>
                <td>
                    <button onclick="deletePedido(${p.id})">Eliminar</button>
                </td>
            </tr>
        `;
    });
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

    await fetch(url, {
        method,
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(producto)
    });

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
    document.getElementById('pedido-items').innerHTML += `
        <div class="item-row">
            <input type="number" placeholder="ID Producto" class="item-id" required>
            <input type="number" placeholder="Cantidad" class="item-cantidad" required>
        </div>
    `;
    pedidoItemCount++;
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

    await fetch(`${apiBaseUrl}/pedidos/nuevo`, {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({ itemsPedido: items })
    });

    hideForm('pedido');
    loadPedidos();
}

async function deletePedido(id) {
    await fetch(`${apiBaseUrl}/pedidos/${id}`, { method: 'DELETE' });
    loadPedidos();
}