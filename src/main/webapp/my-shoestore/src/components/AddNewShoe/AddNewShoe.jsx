
import "./AddNewShoe.css";
import React, {useState} from "react";
import {useNavigate} from "react-router";
import app from "../../App";

function AddNewShoe() {
    const[name, setName]=useState('')
    const[size, setSize]=useState('')
    const[color, setColor]=useState('')
    const[season, setSeason]=useState('')
    const[sex, setSex]=useState('')
    const[actualPrice, setActualPrice]=useState('')
    const[amount, setAmount]=useState('');
    const[image, setImage]=useState('');
    const navigate = useNavigate();

   /* const multer = require('multer');
    const storage = multer.diskStorage({
        destination: function (req, file, cb) {
            cb(null, './assets/') // папка, куди будуть зберігатися завантажені файли
        },
        filename: function (req, file, cb) {
            cb(null, file.originalname) // назва файлу буде оригінальною
        }
    });
    const upload = multer({ storage: storage });

    app.post('/assets', upload.single('image'), function (req, res) {
        console.log(req.file); // виводимо інформацію про завантажений файл
        // додайте код для збереження шляху до файлу у базі даних
    });

    function handleFileChange(event) {
        const file = event.target.files[0];
        const formData = new FormData();
        formData.append('image', file);

        fetch('/assets', {
            method: 'POST',
            body: formData,
        })
            .then((response) => {
                // обробити відповідь сервера
            })
            .catch((error) => {
                // обробити помилку
            });
    }*/
    const handleSubmit = event => {
        event.preventDefault();
        const shoe = {size, color, season, sex, actualPrice, name, amount, image}
        fetch("http://localhost:3000/addnewshoe", {
            method: "POST",
            headers: {"Content-Type": "application/json"},
            body: JSON.stringify(shoe)
        }).then(() => {
                localStorage.setItem('shoe', JSON.stringify(shoe));
                window.dispatchEvent(new Event('storage'));
                navigate('/main');
            }
        )
    };

    return (
        <div className="add-new-shoe">
            <h1 className="add-new-shoe-header">Додати новий товар</h1>
            <div className="add-new-shoe-form">

                <form className="add-new-shoe-form--form" onSubmit={handleSubmit}>
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
                    <input className="add-new-shoe-form-input"
                           type="text"
                           name="size"
                           value={size}
                           onChange={event => setSize(event.target.value)}
                    />
                    <label className="add-new-shoe-form-label">
                        Color</label>
                    <input className="add-new-shoe-form-input"
                           type="text"
                           name="color"
                           value={color}
                           onChange={event => setColor(event.target.value)}
                    />
                    <label className="add-new-shoe-form-label">
                        Season:</label>
                    <input className="add-new-shoe-form-input"
                           type="text"
                           name="season"
                           value={season}
                           onChange={event => setSeason(event.target.value)}
                    />

                    <label className="add-new-shoe-form-label">
                        Sex:</label>
                    <input className="add-new-shoe-form-input"
                           type="text"
                           name="sex"
                           value={sex}
                           onChange={event => setSex(event.target.value)}
                    />

                    <label className="add-new-shoe-form-label">
                        Price</label>
                    <input className="add-new-shoe-form-input"
                           type="text"
                           name="price"
                           value={actualPrice}
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
                    <input className="add-new-shoe-form-input"
                           type="file"
                           name="image"
                           //onChange={handleFileChange}
                           style={{ display: 'none' }}
                    />
                    <button onClick={() => document.getElementsByName('image')[0].click()}>
                        Додати фото товару
                    </button>

                    <button className="add-new-shoe-form-btn" onClick={handleSubmit} type="submit">Додати</button>
                </form>
            </div>
        </div>

        )
}

export default AddNewShoe;