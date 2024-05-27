import './App.css';
import api from './api/axiosConfig';
import {useState, useEffect } from 'react';

function App() { 
  const [products, setProducts] = useState([]); 

  const getProducts = async () => {  
    
    try { 
      const response = await api.get("/v1/products"); 

      console.log("API response: ", response.data);
      
      setProducts(response.data); 

    } catch (error) { 
      console.log(error); 
    }

  }

  useEffect(() => { 
    getProducts(); 
  }, []);

  return (
    <div className="App">
    <h1>Products</h1>
    <div>
      {products.length > 0 ? (
        products.map((product, index) => {
          return (
            <div key={index}>
              <h3>{product.name}</h3>
              <p>{product.description}</p>
              <p>{product.price}</p>
            </div>
          );
        })
      ) : (
        <p>No products available.</p>
      )}
    </div>
  </div>
);
}

export default App;
