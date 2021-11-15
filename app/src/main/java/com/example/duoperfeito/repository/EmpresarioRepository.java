package com.example.duoperfeito.repository;

import android.content.Context;

import com.example.duoperfeito.asynctask.BaseAsyncTask;
import com.example.duoperfeito.database.DuoPerfeitoDatabase;
import com.example.duoperfeito.database.dao.EmpresarioDAO;
import com.example.duoperfeito.model.Empresario;
import com.example.duoperfeito.retrofit.DuoPerfeitoRetrofit;
import com.example.duoperfeito.retrofit.callback.CallbackComRetorno;
import com.example.duoperfeito.retrofit.callback.CallbackSemRetorno;
import com.example.duoperfeito.retrofit.service.EmpresarioService;

import java.util.List;

import retrofit2.Call;

public class EmpresarioRepository {

    private final EmpresarioDAO dao;
    private final EmpresarioService service;

    public EmpresarioRepository(Context context) {
        DuoPerfeitoDatabase db = DuoPerfeitoDatabase.getInstance(context);
        dao = db.getEmpresarioDAO();
        service = new DuoPerfeitoRetrofit().getEmpresarioService();
    }

    public void buscaEmpresarios(DadosCarregadosCallback<List<Empresario>> callback) {
        buscaEmpresariosInternos(callback);
    }

    private void buscaEmpresariosInternos(DadosCarregadosCallback<List<Empresario>> callback) {
        new BaseAsyncTask<>(dao::buscarTodos,
                resultado -> {
                    callback.quandoSucesso(resultado);
                    buscaEmpresariosNaApi(callback);
                }).execute();
    }

    private void buscaEmpresariosNaApi(DadosCarregadosCallback<List<Empresario>> callback) {
        Call<List<Empresario>> call = service.buscaTodos();
        call.enqueue(new CallbackComRetorno<>(
                new CallbackComRetorno.RespostaCallback<List<Empresario>>() {
            @Override
            public void quandoSucesso(List<Empresario> empresariosNovos) {
                atualizaInterno(empresariosNovos, callback);
            }

            @Override
            public void quandoFalha(String erro) {
                callback.quandoFalha(erro);
            }
        }));
    }

    private void atualizaInterno(List<Empresario> empresarios,
                                 DadosCarregadosCallback<List<Empresario>> callback) {
        new BaseAsyncTask<>(() -> {
            dao.salvar(empresarios);
            return dao.buscarTodos();
        }, callback::quandoSucesso)
                .execute();
    }

    public void salva(Empresario empresario,
                      DadosCarregadosCallback<Empresario> callback) {
        salvaNaApi(empresario, callback);
    }

    private void salvaNaApi(Empresario empresario,
                            DadosCarregadosCallback<Empresario> callback) {
        Call<Empresario> call = service.salva(empresario);
        call.enqueue(new CallbackComRetorno<>(
                new CallbackComRetorno.RespostaCallback<Empresario>() {
            @Override
            public void quandoSucesso(Empresario empresarioSalvo) {
                salvaInterno(empresarioSalvo, callback);
            }

            @Override
            public void quandoFalha(String erro) {
                callback.quandoFalha(erro);
            }
        }));
    }

    private void salvaInterno(Empresario empresario,
                              DadosCarregadosCallback<Empresario> callback) {
        new BaseAsyncTask<>(() -> {
            long id = dao.salvar(empresario);
            return dao.buscarEmpresario(id);
        }, callback::quandoSucesso)
                .execute();
    }

    public void edita(Empresario empresario,
                      DadosCarregadosCallback<Empresario> callback) {
        Call<Empresario> call = service.edita(empresario.getId(), empresario);
        call.enqueue(new CallbackComRetorno<>(
                new CallbackComRetorno.RespostaCallback<Empresario>() {
            @Override
            public void quandoSucesso(Empresario resultado) {
                new BaseAsyncTask<>(() -> {
                    dao.atualizar(empresario);
                    return empresario;
                }, callback::quandoSucesso)
                        .execute();
            }

            @Override
            public void quandoFalha(String erro) {
                callback.quandoFalha(erro);
            }
        }));
    }

    public void remove(Empresario empresario,
                       DadosCarregadosCallback<Void> callback) {
        removeNaApi(empresario, callback);
    }

    private void removeNaApi(Empresario empresario,
                             DadosCarregadosCallback<Void> callback) {
        Call<Void> call = service.remove(empresario.getId());
        call.enqueue(new CallbackSemRetorno(
                new CallbackSemRetorno.RespostaCallback() {
            @Override
            public void quandoSucesso() {
                removeInterno(empresario, callback);
            }

            @Override
            public void quandoFalha(String erro) {
                callback.quandoFalha(erro);
            }
        }));
    }

    private void removeInterno(Empresario empresario,
                               DadosCarregadosCallback<Void> callback) {
        new BaseAsyncTask<>(() -> {
            dao.remover(empresario);
            return null;
        }, callback::quandoSucesso)
                .execute();
    }

    public interface DadosCarregadosCallback <T> {
        void quandoSucesso(T resultado);
        void quandoFalha(String erro);
    }

}
