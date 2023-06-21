import React, { useEffect, useState } from 'react';
import { useParams } from 'react-router-dom';

const ProductDetails = () => {
  const { id } = useParams();
  const [product, setProduct] = useState(null);

  useEffect(() => {
    fetchProduct();
  }, []);

  const fetchProduct = async () => {
    const response = await fetch(`/api/products/${id}`);
    const data = await response.json();
    setProduct(data);
  };

  if (!product) {
    return <div>Loading...</div>;
  }

  return (
    <div>
      <h1>Product Details</h1>
      <div>ID: {product.id}</div>
      <div>Name: {product.name}</div>
      <div>Price: {product.price}</div>
      <img src={product.imageKey} alt={product.name} />
    </div>
  );
};

export default ProductDetails;
