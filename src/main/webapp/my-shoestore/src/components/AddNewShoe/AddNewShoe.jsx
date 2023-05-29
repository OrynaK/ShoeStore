import "./AddNewShoe.css";
import React, {useRef, useState} from "react";
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
    const [errorMessage, setErrorMessage] = useState('');

    const navigate = useNavigate();


    const handleImageChange = (event) => {
        setImage(event.target.files[0]);
    };

    const handleSubmit = event => {
        if (
            name === '' ||
            size === '' ||
            color === '' ||
            season === '' ||
            sex === '' ||
            price === '' ||
            amount === '' ||
            !image
        ) {
           alert('Будь ласка, заповніть всі поля');
        } else {
            event.preventDefault();
            const formData = new FormData();
            formData.append('shoeDTO', JSON.stringify({name, size, color, season, sex, price, amount}));
            formData.append('imageData', image);

            fetch("http://localhost:8080/addShoe", {
                method: "POST",
                body: formData
            }).then((response) => {
                if (response.ok) {
                    navigate('/main');
                } else {
                    alert('Сталася помилка під час відправки форми. Спробуйте пізніше');
                }
            });
        }
    };

    return (
        <div className="add-new-shoe">
            <h1 className="add-new-shoe-header">Додати новий товар</h1>
            <div className="add-new-shoe-form">

                <form className="add-new-shoe-form--form">
                    <label className="add-new-shoe-form-label">
                        Назва</label>
                    <input className="add-new-shoe-form-input"
                           type="text"
                           name="name"
                           value={name}
                           onChange={event => setName(event.target.value)}
                    />

                    <label className="add-new-shoe-form-label">
                        Розмір</label>
                    <select className="add-new-shoe-form-input"
                            name="size"
                            value={size}
                            onChange={event => setSize(event.target.value)}
                    >
                        <option value="" disabled>Виберіть розмір</option>
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
                        Колір</label>
                    <select className="add-new-shoe-form-input"
                            name="color"
                            value={color}
                            onChange={event => setColor(event.target.value)}
                    >
                        <option value="" disabled>Виберіть колір</option>
                        <option value="BLACK">Чорний</option>
                        <option value="WHITE">Білий</option>
                        <option value="RED">Червоний</option>
                        <option value="BLUE">Синій</option>
                        <option value="GREEN">Зелений</option>
                    </select>
                    <label className="add-new-shoe-form-label">
                        Сезон</label>
                    <select className="add-new-shoe-form-input"
                            name="season"
                            value={season}
                            onChange={event => setSeason(event.target.value)}
                    >
                        <option value="" disabled>Виберіть сезон</option>
                        <option value="DEMI">Демі</option>
                        <option value="SUMMER">Літо</option>
                        <option value="WINTER">Зима</option>
                    </select>

                    <label className="add-new-shoe-form-label">
                        Стать</label>
                    <select className="add-new-shoe-form-input"
                            name="sex"
                            value={sex}
                            onChange={event => setSex(event.target.value)}
                    >
                        <option value="" disabled>Виберіть стать</option>
                        <option value="MALE">Чоловіча</option>
                        <option value="FEMALE">Жіноча</option>
                    </select>

                    <label className="add-new-shoe-form-label">
                        Ціна</label>
                    <input className="add-new-shoe-form-input"
                           type="number"
                           name="price"
                           value={price}
                           step="0.01"
                           min="0"
                           max="9999999.99"
                           onChange={event => {
                               const inputValue = event.target.value;
                               const regex = /^\d{0,9}(\.\d{0,2})?$/;
                               if (regex.test(inputValue)) {
                                   setActualPrice(inputValue);
                               }
                           }}
                    />

                    <label className="add-new-shoe-form-label">
                        Кількість</label>
                    <input className="add-new-shoe-form-input"
                           type="number"
                           name="amount"
                           value={amount}
                           min="0"
                           step="1"
                           onKeyDown={(event) => {
                               if (!/\d/.test(event.key) && event.key !== "Backspace") {
                                   event.preventDefault();
                               }
                           }}
                           onChange={event => setAmount(event.target.value)}
                    />
                    <label className="add-new-shoe-form-label">
                        Зображення
                    </label>
                    <input
                        className="add-new-shoe-form-input"
                        type="file"
                        accept="image/*"
                        onChange={handleImageChange}
                    />
                    {errorMessage && <p className="error-message">{errorMessage}</p>}
                    {image && <img src={image} alt="Selected"/>}

                    <button className="add-new-shoe-form-btn" onClick={handleSubmit} type="submit">Додати</button>
                </form>
            </div>
        </div>

    )
}

export default AddNewShoe;