import './Hero.css';
import Carousel from 'react-material-ui-carousel';
import { Paper } from '@mui/material';

const Hero = ({ products }) => {
    return (
        <div>
            <Carousel>
                {products.map((product, index) => (
                    <Paper key={index}>
                        <h2>{product.name}</h2>
                        <p>{product.description}</p>
                        <p>Code: {product.code}</p>
                        <p>Seller: {product.seller}</p>
                        <p>Price: {product.price}</p>
                        <p>Status: {product.status ? 'Available' : 'Out of stock'}</p>
                        <p>Message: {product.message}</p>
                        <img src={product.imgs} alt={product.name} style={{ width: '100%' }} />
                    </Paper>
                ))}
            </Carousel>
        </div>
    );
};

export default Hero;
