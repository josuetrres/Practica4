var nodes = new vis.DataSet([
{ id: 0, label: "Capacitor"},
{ id: 1, label: "LED"},
{ id: 2, label: "Resistor"},
{ id: 3, label: "LED"},
{ id: 4, label: "Servomotor"},
]);
var edges = new vis.DataSet([
{ from: 0, to: 1},
{ from: 0, to: 2},
{ from: 0, to: 3},
{ from: 0, to: 4},
{ from: 1, to: 2},
{ from: 1, to: 3},
{ from: 1, to: 4},
{ from: 2, to: 3},
{ from: 2, to: 4},
{ from: 3, to: 4},
]);
var container = document.getElementById("mynetwork");
var data = {
nodes: nodes,
edges: edges,
};
var options = {};
var network = new vis.Network(container, data, options);
