import React, { useState } from 'react';
import axios from 'axios';

function CreateProduct() {
  const [name, setName] = useState('');
  const [price, setPrice] = useState('');
  const [image, setImage] = useState(null);

  const handleNameChange = (event) => {
    setName(event.target.value);
  };

  const handlePriceChange = (event) => {
    setPrice(event.target.value);
  };

  const handleImageChange = (event) => {
    setImage(event.target.files[0]);
  };

  const handleSubmit = (event) => {
    event.preventDefault();

    const productData = {
      name: name,
      price: price,
    };

    const formData = new FormData();
    formData.append('product', JSON.stringify(productData));
    formData.append('file', image);

    axios.post('/api/products/create', formData, {
      headers: {
        'Content-Type': 'multipart/form-data',
      },
    })
      .then((response) => {
        console.log('Product created successfully', response.data);
        // Perform any additional actions after successful product creation
      })
      .catch((error) => {
        console.error('Error creating product', error);
        // Handle error cases
      });
  };

  return (
    <div>
      <h2>Create Product</h2>
      <form onSubmit={handleSubmit}>
        <div>
          <label>Name:</label>
          <input type="text" value={name} onChange={handleNameChange} />
        </div>
        <div>
          <label>Price:</label>
          <input type="number" value={price} onChange={handlePriceChange} />
        </div>
        <div>
          <label>Image:</label>
          <input type="file" onChange={handleImageChange} />
        </div>
        <button type="submit">Create</button>
      </form>
    </div>
  );
}

export default CreateProduct;
