from flask import Blueprint, abort, jsonify, request, render_template, redirect,url_for, flash
import requests
router = Blueprint('router', __name__)




@router.route('/')
def home():
    return render_template('home.html')

@router.route('/componentList')
def component_list():
    r = requests.get('http://localhost:8080/myapp/component/all')
    data =  r.json()
    return render_template('componentAll.html', lista = data["data"])

@router.route('/saveComponent', methods=['GET', 'POST'])
def save_component():
    if request.method == 'POST':
        component_data = {
            "id": request.form['id'],
            "type": request.form['type'],
            "value": request.form['value'],
            "description": request.form['description'],
        }
        
        response = requests.post('http://localhost:8080/myapp/component/save', json=component_data)
        if response.status_code == 200:
            return redirect(url_for('router.component_list'))  
        else:
            return render_template('saveComponent.html', error=response.json().get("data", "Error desconocido"))
    
    return render_template('saveComponent.html')


    

@router.route('/updateComponent/<int:component_id>', methods=['GET', 'POST'])
def update_component(component_id):
    if request.method == 'POST':
        component_data = {
            "id": component_id,
            "type": request.form['type'],
            "value": request.form['value'],
            "description": request.form['description'],
        }
        
        response = requests.post('http://localhost:8080/myapp/component/update', json=component_data)
        if response.status_code == 200:
            flash("Componente actualizado correctamente", "success")
            return redirect(url_for('router.component_list'))  
        else:
            flash(response.json().get("data", "Error al actualizar el componente"), "danger")
    
    response = requests.get(f'http://localhost:8080/myapp/component/get/{component_id}')
    if response.status_code == 200:
        component = response.json().get("data", {})
    else:
        flash("No se pudo encontrar el componente", "danger")
        return redirect(url_for('router.component_list'))
    
    return render_template('updateComponent.html', component=component)


@router.route('/deleteComponent/<int:id>', methods=['POST'])
def delete_component(id):
    
    response = requests.post(f'http://localhost:8080/myapp/component/delete/{id}')
    
    if response.status_code == 200:
        flash('Componente eliminado correctamente', 'success')
        return redirect(url_for('router.home'))  
    else:
        flash(response.json().get("info", "Error al eliminar el componente"), 'danger')
        return redirect(url_for('router.home'))  


@router.route('/updateGraph', methods=['POST'])
def update_graph():
    message = None
    category = None
    redirect_to = url_for('router.home')  

    try:
        response = requests.post('http://localhost:8080/myapp/graph/update')

        if response.status_code == 200:
            message = 'Grafo actualizado correctamente'
            category = 'success'
        elif response.status_code == 500:
            try:
                error_data = response.json()
                message = error_data.get("error", "Error desconocido")
                category = 'danger'
            except ValueError:
                message = "Error desconocido al actualizar el grafo"
                category = 'danger'
        else:
            message = f"Error inesperado: {response.status_code}"
            category = 'danger'

    except requests.exceptions.RequestException as e:
        message = f"Error de conexión: {e}"
        category = 'danger'

    if message:  
        flash(message, category)

    return redirect(redirect_to) 


@router.route('/graph')
def graph():
    return render_template('grafo.html')

@router.route('/addEdge', methods=['GET', 'POST'])
def add_edge():
    response = None
    
    r = requests.get('http://localhost:8080/myapp/component/all')
    data = r.json()
    components = data.get("data", [])

    if request.method == 'POST':
        id1 = int(request.form['id1'])
        id2 = int(request.form['id2'])
        weight = float(request.form['weight'])
        
      
        r = requests.post(f'http://localhost:8080/myapp/graph/addEdge/{id1}/{id2}/{weight}')
        result = r.json()

      
        response = {
            "msg": result.get("msg", "Error"),
            "data": result.get("data", "No se pudo agregar la arista.")
        }

    return render_template('addEdge.html', components=components, response=response)

@router.route('/getGraph', methods=['GET'])
def get_graph():

    r = requests.get('http://localhost:8080/myapp/graph/get')
    

    graph_data = r.json()
    return render_template('graphCircuito.html', data=graph_data)