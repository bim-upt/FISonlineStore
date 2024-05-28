import React from 'react'; 
import Carousel from 'react-material-ui-carousel';
import { Paper } from '@mui/material';

const Home = ({ products }) => {
    return (
        <div>
            <h6 style={{ textAlign: 'right' }}>made by Brebu Iasmin & Burada Andrei</h6>
            <ul>
                {products.map((product, index) => (
                    <li key={index}>
                        <h2>{product.name}</h2>
                        <p>{product.description}</p>
                        <p>Price: {product.price}</p>
                    </li>
                ))}
            </ul>
        </div>
    );
};

export default Home;
