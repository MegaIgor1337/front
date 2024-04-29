import React from "react";
import Modal from 'react-modal';
import "../css/loginModal.css"; // Создайте файл стилей для окна модального ввода
import LoginContainer from "./LoginContainer";

Modal.setAppElement('#root'); // Установите основной элемент здесь


class LoginModal extends React.Component {

  constructor(props) {
    super(props);
    this.state = {
      isClosing: false,
    };
  }

  closeModal = () => {
    this.setState({ isClosing: true });
    setTimeout(() => {
      this.props.closeModal();
      this.setState({ isClosing: false });
    }, 500); // Задержка должна соответствовать времени анимации закрытия (в миллисекундах)
  };
  
  render() {
    const { showModal } = this.props;
    const { isClosing } = this.state;

    return (
      <Modal
        isOpen={showModal}
        onRequestClose={this.closeModal}
        className={`modal-content ${isClosing ? 'ReactModal__Content--before-close' : ''}`}
        overlayClassName="modal-overlay"
      >
       <LoginContainer/>
      </Modal>
    );
  }
}


export default LoginModal;
