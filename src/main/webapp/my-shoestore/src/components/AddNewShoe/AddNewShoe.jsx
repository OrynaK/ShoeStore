import "./AddNewShoe.css";
import React, {useState} from "react";
import {useNavigate} from "react-router";

function AddNewShoe() {
    const [name, setName] = useState('')
    const [size, setSize] = useState('')
    const [color, setColor] = useState('')
    const [season, setSeason] = useState('')
    const [sex, setSex] = useState('')
    const [price, setActualPrice] = useState('')
    const [amount, setAmount] = useState('');
    const [image, setImage] = useState();
    const navigate = useNavigate();

    const handleImageChange = (event) => {
        setImage(event.target.files[0]);
    };

    const handleSubmit = event => {
        event.preventDefault();
        const formData = new FormData();
        formData.append('shoeDTO', JSON.stringify({ name, size, color, season, sex, price, amount }));
        formData.append('imageData', image);

        fetch("http://localhost:8080/addShoe", {
            method: "POST",
            body: formData
        }).then((response) => {
            if (response.ok) {
                navigate('/main');
            }
        });
    };

    return (
        <div className="add-new-shoe">
            <h1 className="add-new-shoe-header">Додати новий товар</h1>
            <div className="add-new-shoe-form">

                <form className="add-new-shoe-form--form">
                    <label className="add-new-shoe-form-label">
                        Name:</label>
                    <input className="add-new-shoe-form-input"
                           type="text"
                           name="name"
                           value={name}
                           onChange={event => setName(event.target.value)}
                    />

                    <label className="add-new-shoe-form-label">
                        Size:</label>
                    <select className="add-new-shoe-form-input"
                            name="size"
                            value={size}
                            onChange={event => setSize(event.target.value)}
                    >
                        <option value="" disabled>Select size</option>
                        <option value="30">30</option>
                        <option value="31">31</option>
                        <option value="32">32</option>
                        <option value="33">33</option>
                        <option value="34">34</option>
                        <option value="35">35</option>
                        <option value="36">36</option>
                        <option value="37">37</option>
                        <option value="38">38</option>
                        <option value="39">39</option>
                        <option value="40">40</option>
                        <option value="41">41</option>
                        <option value="42">42</option>
                    </select>
                    <label className="add-new-shoe-form-label">
                        Color</label>
                    <select className="add-new-shoe-form-input"
                            name="color"
                            value={color}
                            onChange={event => setColor(event.target.value)}
                    >
                        <option value="" disabled>Select color</option>
                        <option value="BLACK">Black</option>
                        <option value="WHITE">White</option>
                        <option value="RED">Red</option>
                        <option value="BLUE">Blue</option>
                        <option value="GREEN">Green</option>
                    </select>
                    <label className="add-new-shoe-form-label">
                        Season: </label>
                    <select className="add-new-shoe-form-input"
                            name="season"
                            value={season}
                            onChange={event => setSeason(event.target.value)}
                    >
                        <option value="" disabled>Select season</option>
                        <option value="DEMI">Demi</option>
                        <option value="SUMMER">Summer</option>
                        <option value="WINTER">Winter</option>
                    </select>

                    <label className="add-new-shoe-form-label">
                        Sex:</label>
                    <select className="add-new-shoe-form-input"
                            name="sex"
                            value={sex}
                            onChange={event => setSex(event.target.value)}
                    >
                        <option value="" disabled>Select sex</option>
                        <option value="MALE">Male</option>
                        <option value="FEMALE">Female</option>
                    </select>

                    <label className="add-new-shoe-form-label">
                        Price</label>
                    <input className="add-new-shoe-form-input"
                           type="text"
                           name="price"
                           value={price}
                           onChange={event => setActualPrice(event.target.value)}
                    />
                    <label className="add-new-shoe-form-label">
                        Amount</label>
                    <input className="add-new-shoe-form-input"
                           type="text"
                           name="amount"
                           value={amount}
                           onChange={event => setAmount(event.target.value)}
                    />
                    <label className="add-new-shoe-form-label">
                        Image
                    </label>
                    <input
                        className="add-new-shoe-form-input"
                        type="file"
                        accept="image/*"
                        onChange={handleImageChange}
                    />
                    {image && <img src={image} alt="Selected"/>}

                    <button className="add-new-shoe-form-btn" onClick={handleSubmit} type="submit">Додати</button>
                </form>
            </div>
        </div>

    )
}

export default AddNewShoe;