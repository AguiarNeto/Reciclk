require('dotenv').config();
const express = require('express');
const nodemailer = require('nodemailer');
const bodyParser = require('body-parser');
const rateLimit = require('express-rate-limit');

const app = express();

// Configuração do rate limiting para prevenir abuso
const limiter = rateLimit({
  windowMs: 15 * 60 * 1000, // 15 minutos
  max: 100 // limite de 100 requisições por IP
});
app.use(limiter);

app.use(bodyParser.json());

// Configuração do transporter de email
const transporter = nodemailer.createTransport({
  service: 'Gmail', // ou outro serviço
  auth: {
    user: process.env.EMAIL_USER,
    pass: process.env.EMAIL_PASS
  }
});

// Rota para lidar com tentativas falhas
app.post('/api/log-failed-attempt', async (req, res) => {
  const { username, timestamp, ipAddress } = req.body;

  try {
    // 1. Registrar no banco de dados (exemplo simplificado)
    // await FailedAttempt.create({ username, timestamp, ipAddress });
    
    // 2. Verificar se é necessário enviar alerta (ex: após 3 tentativas)
    const recentAttempts = 3; // Substitua por consulta real ao banco
    
    if (recentAttempts >= 3) {
      await sendAlertEmail(username, ipAddress, timestamp);
    }

    res.status(200).send('Registrado com sucesso');
  } catch (error) {
    console.error('Erro no servidor:', error);
    res.status(500).send('Erro interno do servidor');
  }
});

// Função para enviar email de alerta
async function sendAlertEmail(username, ipAddress, timestamp) {
  const mailOptions = {
    from: `"Sistema de Segurança" <${process.env.EMAIL_USER}>`,
    to: process.env.ADMIN_EMAIL, // ou buscar do banco de dados
    subject: '⚠️ Alerta: Tentativa de Login Falha',
    html: `
      <h2>Tentativa de Login Falha Detectada</h2>
      <p><strong>Usuário:</strong> ${username}</p>
      <p><strong>IP:</strong> ${ipAddress}</p>
      <p><strong>Horário:</strong> ${new Date(timestamp).toLocaleString()}</p>
      <p><strong>Localização aproximada:</strong> <a href="https://whatismyipaddress.com/ip/${ipAddress}" target="_blank">Ver detalhes</a></p>
      <br>
      <p>Atenciosamente,<br>Sistema de Segurança</p>
    `
  };

  await transporter.sendMail(mailOptions);
}

// Iniciar servidor
const PORT = process.env.PORT || 3000;
app.listen(PORT, () => {
  console.log(`Servidor rodando na porta ${PORT}`);
});