import React, { useState } from 'react';
import Button from 'react-bootstrap/Button';
import Container from 'react-bootstrap/Container';
import Nav from "react-bootstrap/Nav";
import Navbar from "react-bootstrap/Navbar";
import { NavLink } from "react-router-dom";
import "./HeaderStyle.css";

const Header = () => {
    const [showMenu, setShowMenu] = useState(false);
    const [message, setMessage] = useState("");

    const handleMenuClick = () => {
        setShowMenu(!showMenu);
    };

    const handleButtonClick = async (action) => {
        try {
            let response;
            switch (action) {
                case 'getAllProducts':
                    response = await axios.get('/v1/products');
                    setMessage(JSON.stringify(response.data));
                    break;
                case 'addProduct':
                    response = await axios.post('/v1/products', {
                        name: "Product Name",
                        imgs: "Image URL",
                        seller: "Seller Name",
                        code: "Product Code",
                        price: 10 // Product price
                    });
                    setMessage(JSON.stringify(response.data));
                    break;
                case 'modifyProduct':
                    response = await axios.put('/v1/modify', {
                        name: "Modified Product Name",
                        imgs: "Modified Image URL",
                        seller: "Modified Seller Name",
                        code: "Product Code",
                        price: 20 // Modified product price
                    });
                    setMessage(JSON.stringify(response.data));
                    break;
                case 'deleteProduct':
                    response = await axios.delete(`/v1/products/delete?code=ProductCode&seller=SellerName`);
                    setMessage(JSON.stringify(response.data));
                    break;
                case 'addCard':
                    response = await axios.post('/v1/cards', {
                        number: "Card Number",
                        expDate: "YYYY-MM",
                        cvv: "CVV",
                        amount: 100, // Card amount
                        owner: "Card Owner"
                    });
                    setMessage(JSON.stringify(response.data));
                    break;
                case 'addFundsToCard':
                    response = await axios.put('/v1/cards/addAmount?amount=100', {
                        number: "Card Number",
                        expDate: "YYYY-MM",
                        cvv: "CVV",
                        amount: 100, // Amount to be added/subtracted
                        owner: "Card Owner"
                    });
                    setMessage(JSON.stringify(response.data));
                    break;
                case 'deleteCard':
                    response = await axios.delete('/v1/cards/delete', {
                        data: {
                            number: "Card Number",
                            expDate: "YYYY-MM",
                            cvv: "CVV",
                            amount: 100, // Card amount
                            owner: "Card Owner"
                        }
                    });
                    setMessage(JSON.stringify(response.data));
                    break;
                case 'placeOrder':
                    response = await axios.post('/v1/orders/place', {
                        products: [{ code: "ProductCode", seller: "SellerName", message: "Additional information", status: true }],
                        buyer: "BuyerName"
                    });
                    setMessage(JSON.stringify(response.data));
                    break;
                case 'getBuyerOrders':
                    response = await axios.get(`/v1/orders/buyer/BuyerName`);
                    setMessage(JSON.stringify(response.data));
                    break;
                case 'getSellerOrders':
                    response = await axios.get(`/v1/orders/seller/SellerName`);
                    setMessage(JSON.stringify(response.data));
                    break;
                case 'addUser':
                    response = await axios.post('/v1/users', {
                        name: "Username",
                        password: "Password",
                        type: 0 // 0 for buyer, 1 for seller
                    });
                    setMessage(JSON.stringify(response.data));
                    break;
                case 'getUserInfo':
                    response = await axios.get(`/v1/users/Username`);
                    setMessage(JSON.stringify(response.data));
                    break;
                case 'addToUserHistory':
                    response = await axios.post(`/v1/users/addHistory/Username`, {
                        code: "ProductCode",
                        seller: "SellerName"
                    });
                    setMessage(JSON.stringify(response.data));
                    break;
                case 'getUserHistory':
                    response = await axios.get(`/v1/users/getHistory/Username`);
                    setMessage(JSON.stringify(response.data));
                    break;
                case 'getProductStats':
                    response = await axios.get(`/v1/users/Username/getProductStats?code=ProductCode`);
                    setMessage(JSON.stringify(response.data));
                    break;
                case 'getUserStats':
                    response = await axios.get(`/v1/users/Username/getStats`);
                    setMessage(JSON.stringify(response.data));
                    break;
                case 'deleteUser':
                    response = await axios.delete('/v1/users/delete', {
                        data: {
                            name: "Username",
                            password: "Password",
                            type: 0 // 0 for buyer, 1 for seller
                        }
                    });
                    setMessage(JSON.stringify(response.data));
                    break;
                default:
                    setMessage("Acțiunea butonului nu este gestionată.");
            }
        } catch (error) {
            console.error("Eroare la tratarea cererii:", error);
            setMessage("A apărut o eroare la tratarea cererii. Vă rugăm să încercați din nou mai târziu.");
        }
    };

    return (
        <div>
            <Navbar bg="blue" variant="dark" expand="lg">
                <Container>
                    <Navbar.Toggle aria-controls="basic-navbar-nav" />
                    <Navbar.Collapse id="basic-navbar-nav">
                        <Nav className="me-auto d-flex align-items-center">
                            <Navbar.Brand as={NavLink} to="/" className="d-flex align-items-center">
                                <Button variant="outline-info" className="me-2 menu-button" onClick={handleMenuClick}>Menu</Button>
                            </Navbar.Brand>
                            {showMenu && (
                                <div className="d-flex flex-column">
                                    <Button variant="outline" className="me-2 mb-2" onClick={() => handleButtonClick('getAllProducts')}>Get all products</Button>
                                    <Button variant="outline" className="me-2 mb-2" onClick={() => handleButtonClick('addProduct')}>Add Product</Button>
                                    <Button variant="outline" className="me-2 mb-2" onClick={() => handleButtonClick('modifyProduct')}>Modify Product</Button>
                                    <Button variant="outline" className="me-2 mb-2" onClick={() => handleButtonClick('deleteProduct')}>Delete Product</Button>
                                    <Button variant="outline" className="me-2 mb-2" onClick={() => handleButtonClick('addCard')}>Add Card</Button>
                                    <Button variant="outline" className="me-2 mb-2" onClick={() => handleButtonClick('addFundsToCard')}>Add Funds to Card</Button>
                                    <Button variant="outline" className="me-2 mb-2" onClick={() => handleButtonClick('deleteCard')}>Delete Card</Button>
                                    <Button variant="outline" className="me-2 mb-2" onClick={() => handleButtonClick('placeOrder')}>Place Order</Button>
                                    <Button variant="outline" className="me-2 mb-2" onClick={() => handleButtonClick('getBuyerOrders')}>Get Buyer's Orders</Button>
                                    <Button variant="outline" className="me-2 mb-2" onClick={() => handleButtonClick('getSellerOrders')}>Get Seller's Orders</Button>
                                    <Button variant="outline" className="me-2 mb-2" onClick={() => handleButtonClick('addUser')}>Add User</Button>
                                    <Button variant="outline" className="me-2 mb-2" onClick={() => handleButtonClick('getUserInfo')}>Get User Info</Button>
                                    <Button variant="outline" className="me-2 mb-2" onClick={() => handleButtonClick('addToUserHistory')}>Add to User's History</Button>
                                    <Button variant="outline" className="me-2 mb-2" onClick={() => handleButtonClick('getUserHistory')}>Get User's History</Button>
                                    <Button variant="outline" className="me-2 mb-2" onClick={() => handleButtonClick('getProductStats')}>Get Product Stats</Button>
                                    <Button variant="outline" className="me-2 mb-2" onClick={() => handleButtonClick('getUserStats')}>Get User Stats</Button>
                                    <Button variant="outline" className="me-2 mb-2" onClick={() => handleButtonClick('deleteUser')}>Delete User</Button>
                                </div>
                            )}
                        </Nav>
                    </Navbar.Collapse>
                </Container>
            </Navbar>
            <div>{message}</div>
        </div>
    );
};

export default Header;