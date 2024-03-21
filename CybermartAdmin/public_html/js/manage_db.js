// Initialize Firebase (ADD YOUR OWN DATA)
const firebaseConfig = {
    apiKey: "AIzaSyBo3zm6YaeeuEoZp2TLUg8jbqeYR226Jd0",
    authDomain: "testhtml-218f5.firebaseapp.com",
    databaseURL: "https://testhtml-218f5-default-rtdb.firebaseio.com",
    projectId: "testhtml-218f5",
    storageBucket: "testhtml-218f5.appspot.com",
    messagingSenderId: "1062468553888",
    appId: "1:1062468553888:web:7cf9c878017d3156838dc5",
    measurementId: "G-49C0RFTPGJ"
  };
firebase.initializeApp(firebaseConfig);


var database = firebase.database();
var productsRef = database.ref('cybermart/products'); // Update the reference to the products

// Function to fetch data from Firebase and populate the products table
function ProductTable() {
    productsRef.on('value', function(snapshot) {
        var productTable = document.getElementById('productTable').getElementsByTagName('tbody')[0];
        productTable.innerHTML = ''; // Clear the product table

        snapshot.forEach(function(childSnapshot) {
            var data = childSnapshot.val();
            var key = childSnapshot.key;

            // Create a new row for each product
            var row = productTable.insertRow();
            var cell1 = row.insertCell(0);
            var cell2 = row.insertCell(1);
            var cell3 = row.insertCell(2);
            var cell4 = row.insertCell(3);
            var cell5 = row.insertCell(4);

            cell1.innerHTML = key;
            cell2.innerHTML = data.pname;
            cell3.innerHTML = data.price;
            cell4.innerHTML = data.quantity;

            // Add Edit and Delete buttons for product table
            var editButton = document.createElement('button');
            editButton.innerHTML = 'Edit';
            editButton.addEventListener('click', function() {
                editProduct(key, data.pname, data.price, data.quantity);
                generateQRCode(key+","+data.pname+","+data.quantity+","+data.price);
            });

            var deleteButton = document.createElement('button');
            deleteButton.innerHTML = 'Delete';
            deleteButton.addEventListener('click', function() {
                deleteProduct(key);
            });

            cell5.appendChild(editButton);
            cell5.appendChild(deleteButton);
        });
    });
}

// Function to edit a product using prompts
function editProduct(key, productName, price, quantity) {
    var updatedProductName = prompt('Edit Product Name:', productName);
    var updatedPrice = prompt('Edit Price:', price);
    var updatedQuantity = prompt('Edit Quantity:', quantity);

    if (updatedProductName !== null && updatedPrice !== null && updatedQuantity !== null) {
        // Update the data in Firebase product database if user didn't cancel
        productsRef.child(key).set({
            pid:key,
            pname: updatedProductName,
            price: updatedPrice,
            quantity: updatedQuantity
        });
    alert("Product "+key+" Succesfully Updated!\nThe Updated QR code is downloading.");
    }
}

// Function to delete a product
function deleteProduct(key) {
    var confirmDelete = confirm('Are you sure you want to delete this product?');
    if (confirmDelete) {
        productsRef.child(key).remove();
        alert("Product "+key+" Succesfully deleted!");
    }
}




/////////////////////////////////////////////////////////////////////////////////////////////

var trolleyRef = database.ref('cybermart/trolleys'); // Update the reference to the products

// Function to fetch data from Firebase and populate the products table
function TrolleyTable() {
    trolleyRef.on('value', function(snapshot) {
        var trolleyTable = document.getElementById('trolleyTable').getElementsByTagName('tbody')[0];
        trolleyTable.innerHTML = ''; // Clear the product table

        snapshot.forEach(function(childSnapshot) {
            var data = childSnapshot.val();
            var key = childSnapshot.key;

            // Create a new row for each product
            var row = trolleyTable.insertRow();
            var cell1 = row.insertCell(0);
            var cell2 = row.insertCell(1);
            var cell3 = row.insertCell(2);
            var cell4 = row.insertCell(3);
            
            cell1.innerHTML = key;
            cell2.innerHTML = data.status;
            cell3.innerHTML = data.bid;
            
            // Add Edit and Delete buttons for product table
            var editButton = document.createElement('button');
            editButton.innerHTML = 'Edit';
            editButton.addEventListener('click', function() {
                editTrolley(key, data.status, data.bid);
            });

            var deleteButton = document.createElement('button');
            deleteButton.innerHTML = 'Delete';
            deleteButton.addEventListener('click', function() {
                deleteTrolley(key);
            });

            cell4.appendChild(editButton);
            cell4.appendChild(deleteButton);
        });
    });
}

// Function to edit a product using prompts
function editTrolley(key, status, bid) {
    var updatedStatus = prompt('Edit Trolley Status:', status);
    var updatedBid = prompt('Edit Bill ID:', bid);

    if (updatedStatus !== null && updatedBid !== null) {
        // Update the data in Firebase product database if user didn't cancel
        trolleyRef.child(key).set({
            tid:key,
            status: updatedStatus,
            bid: updatedBid
                });
    alert("Trolley "+key+" Succesfully Updated!");
    }
}

// Function to delete a product
function deleteTrolley(key) {
    var confirmDelete = confirm('Are you sure you want to delete this trolley?');
    if (confirmDelete) {
        trolleyRef.child(key).remove();
        alert("Trolley "+key+" Succesfully deleted!");
    }
}




///////////////////////////////////////////////////////////////////////////////////////////////////////////

// Call the function to fetch and populate the trolley table
TrolleyTable();


// Call the function to fetch and populate the product table
ProductTable();


////// QR CODE GENERATOR
function generateQRCode(qr_text) {
    
    // Create a QR code
    var qrcode = new QRCode(document.getElementById("qrcode"), {
        text: qr_text,
        width: 128,
        height: 128
    });

    // Create a download link for the QR code
    var qrCodeDataUrl = document.getElementById("qrcode").getElementsByTagName("canvas")[0].toDataURL();
    var downloadLink = document.createElement("a");
    downloadLink.href = qrCodeDataUrl;
    downloadLink.download = qr_text+".png";
    downloadLink.click();
}
////// QR CODE GENERATOR