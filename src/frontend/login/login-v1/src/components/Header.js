import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faVideoSlash } from "@fortawesome/free-solid-svg-icons";
import Button from 'react-bootstrap/Button'; // Import corect pentru Button
import Container from 'react-bootstrap/Container';
import Nav from "react-bootstrap/Nav";
import Navbar from "react-bootstrap/Navbar";
import { NavLink } from "react-router-dom"; 
import { useState } from 'react'; 
import "./HeaderStyle.css";

const Header = () => {
    const [showMenu, setShowMenu] = useState(false);

    const handleMenuClick = () => {
        setShowMenu(!showMenu);
    };

    return (
        <Navbar bg="blue" variant="dark" expand="lg"> {/* Utilizare culori valide */}
            <Container>
                <Navbar.Toggle aria-controls="basic-navbar-nav" />
                <Navbar.Collapse id="basic-navbar-nav">
                    <Nav className="me-auto d-flex align-items-center">
                        <Navbar.Brand as={NavLink} to="/" className="d-flex align-items-center">
                            <Button variant="outline-info" className="me-2" onClick={handleMenuClick}>Menu</Button>
                        </Navbar.Brand>
                        {showMenu && (
                            <>
                                <Button variant="outline" className="me-2">Login</Button>
                                <Button variant="outline" className="me-2">Products_List</Button>
                                <Button variant="outline" className="me-2">Add_Product</Button>
                                <Button variant="outline" className="me-2">Modify_Product</Button>
                                <Button variant="outline" className="me-2">Delete_Product</Button>
                            </>
                        )}
                    </Nav>
                </Navbar.Collapse>
            </Container>
        </Navbar>
    );
};

export default Header;
