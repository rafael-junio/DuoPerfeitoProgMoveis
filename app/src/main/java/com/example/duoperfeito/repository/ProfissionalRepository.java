package com.example.duoperfeito.repository;

import android.content.Context;

import com.example.duoperfeito.asynctask.BaseAsyncTask;
import com.example.duoperfeito.database.DuoPerfeitoDatabase;
import com.example.duoperfeito.database.dao.ProfissionalDAO;
import com.example.duoperfeito.model.Profissional;
import com.example.duoperfeito.retrofit.DuoPerfeitoRetrofit;
import com.example.duoperfeito.retrofit.callback.CallbackComRetorno;
import com.example.duoperfeito.retrofit.callback.CallbackSemRetorno;
import com.example.duoperfeito.retrofit.service.ProfissionalService;

import java.util.List;

import retrofit2.Call;

public class ProfissionalRepository {

    private final ProfissionalDAO dao;
    private final ProfissionalService service;

    public ProfissionalRepository(Context context) {
        DuoPerfeitoDatabase db = DuoPerfeitoDatabase.getInstance(context);
        dao = db.getProfissionalDAO();
        service = new DuoPerfeitoRetrofit().getProfissionalService();
    }

    public void buscaProfissionais(DadosCarregadosCallback<List<Profissional>> callback) {
        buscaProfissionaisInternos(callback);
    }

    private void buscaProfissionaisInternos(DadosCarregadosCallback<List<Profissional>> callback) {
        new BaseAsyncTask<>(dao::buscaTodos,
                resultado -> {
                    callback.quandoSucesso(resultado);
                    buscaProfissionaisNaApi(callback);
                }).execute();
    }

    private void buscaProfissionaisNaApi(DadosCarregadosCallback<List<Profissional>> callback) {
        Call<List<Profissional>> call = service.buscaTodos();
        call.enqueue(new CallbackComRetorno<>(
                new CallbackComRetorno.RespostaCallback<List<Profissional>>() {
            @Override
            public void quandoSucesso(List<Profissional> profissionaisNovos) {
                atualizaInterno(profissionaisNovos, callback);
            }

            @Override
            public void quandoFalha(String erro) {
                callback.quandoFalha(erro);
            }
        }));
    }

    private void atualizaInterno(List<Profissional> profissionais,
                                 DadosCarregadosCallback<List<Profissional>> callback) {
        new BaseAsyncTask<>(() -> {
            dao.salvar(profissionais);
            return dao.buscaTodos();
        }, callback::quandoSucesso)
                .execute();
    }

    public void salva(Profissional profissional,
                      DadosCarregadosCallback<Profissional> callback) {
        salvaNaApi(profissional, callback);
    }

    private void salvaNaApi(Profissional profissional,
                            DadosCarregadosCallback<Profissional> callback) {
        Call<Profissional> call = service.salva(profissional);
        call.enqueue(new CallbackComRetorno<>(
                new CallbackComRetorno.RespostaCallback<Profissional>() {
            @Override
            public void quandoSucesso(Profissional profissionalSalvo) {
                salvaInterno(profissionalSalvo, callback);
            }

            @Override
            public void quandoFalha(String erro) {
                callback.quandoFalha(erro);
            }
        }));
    }

    private void salvaInterno(Profissional profissional,
                              DadosCarregadosCallback<Profissional> callback) {
        new BaseAsyncTask<>(() -> {
            long id = dao.salvar(profissional);
            return dao.buscaProfissional(id);
        }, callback::quandoSucesso)
                .execute();
    }

    public void edita(Profissional profissional,
                      DadosCarregadosCallback<Profissional> callback) {
        Call<Profissional> call = service.edita(profissional.getId(), profissional);
        call.enqueue(new CallbackComRetorno<>(
                new CallbackComRetorno.RespostaCallback<Profissional>() {
            @Override
            public void quandoSucesso(Profissional resultado) {
                new BaseAsyncTask<>(() -> {
                    dao.atualiza(profissional);
                    return profissional;
                }, callback::quandoSucesso)
                        .execute();
            }

            @Override
            public void quandoFalha(String erro) {
                callback.quandoFalha(erro);
            }
        }));
    }

    public void remove(Profissional profissional,
                       DadosCarregadosCallback<Void> callback) {
        removeNaApi(profissional, callback);
    }

    private void removeNaApi(Profissional profissional,
                             DadosCarregadosCallback<Void> callback) {
        Call<Void> call = service.remove(profissional.getId());
        call.enqueue(new CallbackSemRetorno(
                new CallbackSemRetorno.RespostaCallback() {
            @Override
            public void quandoSucesso() {
                removeInterno(profissional, callback);
            }

            @Override
            public void quandoFalha(String erro) {
                callback.quandoFalha(erro);
            }
        }));
    }

    private void removeInterno(Profissional profissional,
                               DadosCarregadosCallback<Void> callback) {
        new BaseAsyncTask<>(() -> {
            dao.remove(profissional);
            return null;
        }, callback::quandoSucesso)
                .execute();
    }

    public interface DadosCarregadosCallback <T> {
        void quandoSucesso(T resultado);
        void quandoFalha(String erro);
    }

}
