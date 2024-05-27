import '.App.css';
import api from './api/axiosConfig';
import { useState, useEffect } from 'react';
import layout from './components/Layout'; 
import {Routes, Route} from 'react-router-dom';

function ProductList() {
  const [products, setProducts] = useState([]);

  useEffect(() => {
    async function fetchProducts() {
      try {
        const response = await axios.get('/api/products'); // Aici trebuie să fie adresa corectă a endpoint-ului care returnează produsele
        setProducts(response.data);
      } catch (error) {
        console.error('Error fetching products:', error);
      }
    }

    fetchProducts();
  }, []);

  return (
    <div> 
      
      <h1>Product List</h1>
      <ul>
        {products.map((product, index) => (
          <li key={index}>
            <h2>{product.name}</h2>
            <img src={product.imgs} alt={product.name} />
            <p>Seller: {product.seller}</p>
            <p>Price: {product.price}</p>
            <p>Status: {product.status ? 'Available' : 'Out of stock'}</p>
            <p>Message: {product.message}</p>
          </li>
        ))}
      </ul>
    </div>
  );
}

export default ProductList;
