import React from 'react';
import { Outlet } from 'react-router-dom';
import './Layout.css';

const Layout = () => {
    return (
        <div>
            <header>
                <h1>Welcome to our Online_Store</h1>
            </header>
            <main>
                <Outlet />
            </main>

            <footer>
                
            </footer>
        </div>
    );
};

export default Layout;
