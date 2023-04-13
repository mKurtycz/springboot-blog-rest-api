package priv.mKurtycz.blog.springbootblogrestapi.service;

import priv.mKurtycz.blog.springbootblogrestapi.payload.LoginDTO;
import priv.mKurtycz.blog.springbootblogrestapi.payload.RegisterDTO;

public interface AuthService {
    String login(LoginDTO loginDTO);
    String register(RegisterDTO registerDTO);
}
