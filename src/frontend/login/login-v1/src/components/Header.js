import React, { useState } from 'react';
import Button from 'react-bootstrap/Button';
import Container from 'react-bootstrap/Container';
import Nav from "react-bootstrap/Nav";
import Navbar from "react-bootstrap/Navbar";
import { NavLink } from "react-router-dom";
import axiosInstance from './axiosConfig'; // Importul instanței Axios 
import Carousel from 'react-bootstrap/Carousel'; // Import Carousel
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


            console.log(action);
            
            switch (action) {
                case 'getAllProducts':
                    response = await axiosInstance.get('/v1/products');
                    setMessage(JSON.stringify(response.data));
                    break;
                case 'addProduct':
                    response = await axiosInstance.post('/v1/products', {
                        name: "Product Name",
                        imgs: "Image URL",
                        seller: "Seller Name",
                        code: "Product Code",
                        price: 10 // Product price
                    });
                    setMessage(JSON.stringify(response.data));
                    break;
                case 'modifyProduct':
                    response = await axiosInstance.put('/v1/products/modify', {
                        code: "Product Code",
                        name: "Modified Product Name",
                        imgs: "Modified Image URL",
                        seller: "Modified Seller Name",
                        price: 20 // Modified product price
                    });
                    setMessage(JSON.stringify(response.data));
                    break;
                case 'deleteProduct':
                    response = await axiosInstance.delete('/v1/products/delete', {
                        data: {
                            code: "Product Code",
                            seller: "Seller Name"
                        }
                    });
                    setMessage(JSON.stringify(response.data));
                    break;
                case 'addCard':
                    response = await axiosInstance.post('/v1/cards', {
                        number: "Card Number",
                        expDate: "YYYY-MM",
                        cvv: "CVV",
                        amount: 100, // Card amount
                        owner: "Card Owner"
                    });
                    setMessage(JSON.stringify(response.data));
                    break;
                case 'addFundsToCard':
                    response = await axiosInstance.put('/v1/cards/addFunds', {
                        number: "Card Number",
                        amount: 100 // Amount to be added
                    });
                    setMessage(JSON.stringify(response.data));
                    break;
                case 'deleteCard':
                    response = await axiosInstance.delete('/v1/cards/delete', {
                        data: {
                            number: "Card Number",
                            owner: "Card Owner"
                        }
                    });
                    setMessage(JSON.stringify(response.data));
                    break;
                case 'placeOrder':
                    response = await axiosInstance.post('/v1/orders/place', {
                        products: [{ code: "Product Code", quantity: 1 }], // Product code and quantity
                        buyer: "Buyer Name"
                    });
                    setMessage(JSON.stringify(response.data));
                    break;
                case 'getBuyerOrders':
                    response = await axiosInstance.get(`/v1/orders/buyer/BuyerName`);
                    setMessage(JSON.stringify(response.data));
                    break;
                case 'getSellerOrders':
                    response = await axiosInstance.get(`/v1/orders/seller/SellerName`);
                    setMessage(JSON.stringify(response.data));
                    break;
                case 'addUser':
                    response = await axiosInstance.post('/v1/users', {
                        name: "Username",
                        password: "Password",
                        type: 0 // 0 for buyer, 1 for seller
                    });
                    setMessage(JSON.stringify(response.data));
                    break;
                case 'getUserInfo':
                    response = await axiosInstance.get(`/v1/users/Username`);
                    setMessage(JSON.stringify(response.data));
                    break;
                case 'addToUserHistory':
                    response = await axiosInstance.post(`/v1/users/Username/addHistory`, {
                        code: "Product Code",
                        seller: "Seller Name"
                    });
                    setMessage(JSON.stringify(response.data));
                    break;
                case 'getUserHistory':
                    response = await axiosInstance.get(`/v1/users/Username/history`);
                    setMessage(JSON.stringify(response.data));
                    break;
                case 'getProductStats':
                    response = await axiosInstance.get(`/v1/products/stats?code=ProductCode`);
                    setMessage(JSON.stringify(response.data));
                    break;
                case 'getUserStats':
                    response = await axiosInstance.get(`/v1/users/Username/stats`);
                    setMessage(JSON.stringify(response.data));
                    break;
                case 'deleteUser':
                    response = await axiosInstance.delete('/v1/users/delete', {
                        data: {
                            name: "Username",
                            password: "Password"
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
                        </Nav>
                    </Navbar.Collapse>
                </Container>
            </Navbar>
            <div className="carousel-container">
                {showMenu && (
                    <div className="menu-container">
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
                <Carousel className="carousel">
                    <Carousel.Item>
                        <img
                            className="d-block w-100"
                            src={process.env.PUBLIC_URL + '/Logo.jpeg'} 
                            alt="Logo"
                        />
                        <Carousel.Caption className="carousel-caption-text">
                            <p> </p>
                            <p> </p>
                            <p>^Find anything you want here...^</p>
                        </Carousel.Caption>
                    </Carousel.Item>
                    <Carousel.Item>
                        <img
                            className="d-block w-100"
                            src={process.env.PUBLIC_URL + '/Slide2.jpeg'} 
                            alt="Toys"
                        />
                        <Carousel.Caption>
                        <p> </p>
                            <p> </p>
                            <p>#Starting from many cool toys for each age#</p>
                        </Carousel.Caption>
                    </Carousel.Item>
                    <Carousel.Item>
                        <img
                            className="d-block w-100"
                            src={process.env.PUBLIC_URL + '/Slide3.jpeg'} 
                            alt="Healty food"
                        />
                        <Carousel.Caption>
                        <p> </p>
                            <p> </p>
                            <p>!!!Up to healty-food for every budget!!!</p>
                        </Carousel.Caption>
                    </Carousel.Item>
                </Carousel>
            </div>
            <div>{message}</div>
        </div>
    );
};

export default Header;
